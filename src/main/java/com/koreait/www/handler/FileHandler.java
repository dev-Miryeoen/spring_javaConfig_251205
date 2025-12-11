package com.koreait.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component // 사용자 클래스를 빈으로 등록하는 어노테이션
public class FileHandler {

	// 저장경로
	private final String UP_DIR = "C:\\web_0826_lsw\\_myProject\\_java\\_fileUpload\\";
	
	// multipartfiles[] 파라미터로 받고, List<FileVO> return (+저장)
	public List<FileVO> uploadFile(MultipartFile[] files){
		List<FileVO> flist = new ArrayList<FileVO>();
		
		// fileVO 생성 + 파일 저장 + 이미지파일일 경우 썸네일 저장
		// 일반적으로 파일 저장시 날짜별로 폴더화하여 업로드된 파일 관리
		// 2025-12-10 => 2025/12/10
		LocalDate date = LocalDate.now(); // 오늘 날짜 리턴 2025-12-10
		log.info(">>> date >> {}", date);
		String today = date.toString();
		today = today.replace("-", File.separator); // window(\) mac(/)
		
		File folders = new File(UP_DIR, today);
		// mkdir : 폴더 생성 명령어 (1개만 생성) / mkdirs : 하위폴더까지 생성
		if(!folders.exists()) {
			// 폴더가 없다면 생성
			folders.mkdirs();
		}
		
		// files를 가지고 fileVO 생성
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			// uuid, saveDir, fileName, fileType, fileSize
			fvo.setSaveDir(today); // 공통경로 빼고 개별 경로만 저장
			log.info(" getName >> {}",file.getName()); // 변수이름
			log.info(" getOOriginalFileName >> {}",file.getOriginalFilename()); // 실제 파일이름
			String fileName = file.getOriginalFilename(); 
			fvo.setFileName(fileName);
			fvo.setFileSize(file.getSize());
			UUID uuid = UUID.randomUUID();
			fvo.setUuid(uuid.toString());
			// ---- fvo 생성 완료 => fileType, bno
			// 디스크에 저장
			
			String fullFileName = uuid.toString()+"_"+fileName;
			File storeFile = new File(folders, fullFileName);
			
			// 저장
			try {
				file.transferTo(storeFile); // 저장
				
				// 이미지인지 확인 => 이미지만 썸네일 저장
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);
					// 썸네일 생성
					File thumbNail = new File(folders, uuid.toString()+"_th_"+fileName);
					Thumbnails.of(storeFile).size(100, 100).toFile(thumbNail);
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.info("file store error");
				e.printStackTrace();
			}
			// for문 안
			flist.add(fvo);
		}
		
		return flist;
	}
	private boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile);
		// type "image/png" "image/jpeg"
		return mimeType.startsWith("image");
	}
}
