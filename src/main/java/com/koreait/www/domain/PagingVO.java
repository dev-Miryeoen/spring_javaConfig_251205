package com.koreait.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Setter
@Getter
public class PagingVO {
	private int pageNo; // 선택한 하단 페이지네이션 번호
	private int qty; // 한 페이지에 표시되는 게시물 수
	
	// search 변수
	private String type;
	private String keyword;
	
	public PagingVO() {
		this.pageNo = 1;
		this.qty = 10;
	}
	
	// 검색을 하지 않는 케이스
	public PagingVO(int pageNo, int qty) {
		setPageNo(pageNo);
		setQty(qty);
	}
	
	// DB에서 사용될 데이터 설정 (시작번지 구하기)
	// select * from board limit 번지, 개수  => 0부터 시작
	// 1=>0,10	 /  2=>10,10  / 3=>20,10
	public int getPageStart() {
		return (this.pageNo-1) * this.qty;
	}
	
	// type여러 타입을 검색하는 복합타입을 각각 검색으로 분할하기 위해 배열로 설정
	// type="twc" ["t","w","c"]
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {} : this.type.split("");
	}
}
