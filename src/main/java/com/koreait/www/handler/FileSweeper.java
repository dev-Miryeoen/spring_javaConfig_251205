package com.koreait.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.koreait.www.domain.FileVO;
import com.koreait.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@EnableScheduling
@Slf4j
@Component
public class FileSweeper {

// 매일 등록된 시간에 파일 정리 => 스케줄러가 실행
// 화면상에서는(화면+DB) 삭제되었지만, 실제 폴더에는 파일이 남아있기 때문에 파일을 제거
// cron 방식 : 초 분 시 일 월 요일 년도(생략가능)
// cron="59 59 23 * * *" : 매일 23시 59분 59초에 실행

// DB에서 오늘 날짜의 파일을 모두 가져오기
// 파일 객체로 생성
// 파일 리스트와 폴더 안의 파일과 비교 => 없는 파일은 제거
// DB에 존재하지 않는 파일을 삭제(폴더에서)
	
	// 직접 DB에 접근하여 파일리스트를 가져와야 함.
	private final FileDAO fdao;
	
	// 파일 경로
	private final String DIR = "C:\\web_0826_lsw\\_myProject\\_java\\_fileUpload\\";
	
	@Scheduled(cron = "50 27 12 * * *")
	public void fileSweeper() {
		log.info(">>> FileSweeper Running Start!! : {}", LocalDateTime.now());
		
		// DB에 등록된 파일 리스트를 가져오기
		// 오늘 날짜의 파일 리스트를 가져오기
		LocalDate now = LocalDate.now(); // 2025-12-11
		String today = now.toString();
		today = today.replace("-", File.separator);
		
		// select * from file where save_dir = today
		// 파일 리스트 가져오기
		List<FileVO> dbList = fdao.getTodayFileList(today);
		log.info(">>> dbList >> {}", dbList);
		
		// FileVO => String (uuid_fileName , uuid_th_fileName)
		List<String> currFiles = new ArrayList<String>();
		for(FileVO fvo : dbList) {
			String fileName = today+File.separator+fvo.getUuid()+"_"+fvo.getFileName();
			currFiles.add(DIR + fileName);
			
			// 이미지라면 썸네일도 추가
			if(fvo.getFileType() == 1) {
				String thfileName = today+File.separator+fvo.getUuid()+"_th_"+fvo.getFileName();
				currFiles.add(DIR+thfileName);
			}
		}
		log.info(">>> currFiles >>{}", currFiles);
		
		// C:\web_0826_lsw\_myProject\_java\_fileUpload\2025\12\11
		// 경로 기반 저장된 파일 검색
		File dir = Paths.get(DIR+today).toFile();
		
		// listFiles() : 경로안에 있는 파일 배열로 리턴
		File[] allFileObject = dir.listFiles();
		
		// 실제 저장되어 있는 파일 목록과, DB에 존재하는 파일 리스트와 비교하여
		// DB에 없는 파일은 삭제
		for(File file : allFileObject) {
			String storedFileName = file.toPath().toString();
			if(!currFiles.contains(storedFileName)) {
				file.delete(); // 존재하지 않는다면 삭제
				log.info(">>> delete file >> {}", storedFileName);
			}
		}
		
		log.info(">>> FileSweeper Running End!! : {}", LocalDateTime.now());
	}
	
}
