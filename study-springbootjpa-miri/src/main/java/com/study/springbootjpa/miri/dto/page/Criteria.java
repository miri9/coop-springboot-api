// package com.study.springbootjpa.miri.dto.page;

// import lombok.Getter;
// import lombok.ToString;

// /**
//  * Criteria
//  * 
//  * by miri
//  * 
//  * 오로지 현재 페이지의 정보(현재 선택된 페이지, 검색 조건 등)를 담는다.
//  * 화면의 페이징 블럭  [1|2|"3"|4|5|] .... 을 직접 계산하고 시각화 하는 역할은, Pagination 클래스에서 맡는다.
//  * 요컨대, 현재 페이지 정보(Criteria) 를 이용해 페이지네이션 버튼을 계산하는 클래스가 Pagination 이다.
//  * 
//  * [필드]
//  * (필수) : 게시판 리스트를 조회하기 위해 필수적인 정보.
//  * (선택) : 필수 X
//  * 
//  * Integer page (필수) : 현재 페이지 (기본값 1)
//  * Integer amount (필수) : 한 페이지에 n 개씩 보여주기 (기본값 5 -> 프론트에 따라 다름)
//  * 
//  * String type (선택) : 검색 조건 타입. 예컨대 제목(T),내용(C),작성자(W) 가 검색 대상일 때
//  * 제목+내용 을 검색하고자 한다면 프론트에서 백으로 type="TC" 를 보내는 방식이다.
//  * (아직 검색 조건은 구현되지 않았으며, 위와 같은 예는 임시 예시일 뿐임.)
//  * String keyword (선택) : 검색 키워드.
//  * 
//  * [메서드] (기본 getter,setter 는 제외하여 요약함)
//  * - getSkip() : Pagination 클래스에서 페이지네이션 계산하기 위해 사용
//  * - getTypeArr() : Criteria.type 의 값을 split 하여 String[] 에 담아 반환. 추후 검색조건 구현 시 분기문 용도로 사용
//  * 
//  * [가정]
//  *  front 에서 쿼리스트링으로 page=?,amount=? 보내면, 백엔드는 다음과 같은 데이터를 반환한다.
//     -1, 10 :
//     board(id=1)~board(id=10) 을 Set<Board> 으로 반환.
//     -5, 5 : 페이지가 <|1|2|3|4|"5"|6|7|8|9|10|> 일 때, 한 페이지당 5개의 게시물을 가진다.
    
//     -10, 2 : 페이지가 <|"11"|12|13|14|15|16|17|18|19|20|> 일 때, 한 페이지당 2개의 게시물을 가진다.  
//  */
// @Getter
// @ToString
// public class Criteria {
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
