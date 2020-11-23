// package com.study.springbootjpa.miri.dto.page.test;

// import lombok.Getter;
// import lombok.ToString;

// /**
//  * Criteria
//  * 
//  * by miri
//  * 
//  * 오로지 현재 페이지의 정보를 담는다.
//  * 프론트에서 쿼리 스트링으로 페이징 값을 보존하기 위해 사용할 수도 있다.
//  * (예: 게시물 조회하다가 목록 버튼 누르면 1페이지 아닌 기존에 있었던 페이지로 넘어가는 등 )
//  * 
//  * [필드]
//  * - Integer page: 현재 페이지 쪽수
//  * - Integer amount: 화면에서 n개씩 보기
//  * - String type:
//  */
// @Getter
// @ToString
// public class CurrentPage {
//     private Integer page; // 현재 페이지
//     private Integer amount; //  화면에서 n개씩 보기


//     private String type; // 검색조건
//     private String keyword; // 검색키워드

//     // start 생성자
//     /**
//      * 기본 생성자 : 사용하지 말것.
//      */
//     public Criteria(){};

//     /**
//      * 현재 페이지 쪽수와 보여줄 게시물 수를 이용해 초기화
//      * @param page 현재 페이지 쪽수
//      * @param amount 현재 페이지에 보여줄 게시물 수
//      */
//     public Criteria(Integer page, Integer amount){
//         this.page = page == null || page<=0 ? 1:page;
//         this.amount = amount==null || amount<=0 ? 5:amount;
//     }
//     // end 생성자



//     // start 일반 필드 getter,setter
//     public void setPage(Integer page) {
//         this.page = page==null||page<=0 ?1:page;
//     }

//     public void setAmount(Integer amount) {
//         this.amount = amount==null||amount<=10 ?10:amount;
//     }
//     // end 일반 필드 getter,setter
    



//     // start 페이지 관련 로직
//     /**
//      *  Pagination 클래스에서 페이지네이션 계산하기 위해 사용
//      * @return (page-1) * amount
//      */
//     public int getSkip() {
//         return (page-1) * amount;
// 	}
    
//     /**
//      * Criteria.type 의 값을 split 하여 String[] 에 담아 반환. 추후 검색조건 구현 시 분기문 용도로 사용
//      * @return String[검색조건A, B, C]
//      */
//     public String[] getTypeArr() {
//         return type == null||type.isBlank()? new String[] {}: type.split("");
//     }
//     // end 페이지 관련 로직
    
	
    
// }
