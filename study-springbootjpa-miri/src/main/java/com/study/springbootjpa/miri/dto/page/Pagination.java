package com.study.springbootjpa.miri.dto.page;
/**
 * pagination
 * 
 * by miri
 * 
 * 현재 페이지 정보(Criteria) 를 기반으로 화면에 보여줄 페이지네이션 버튼 데이터 값을 계산한다.
 * 
 * [필드]
 * - int startPage : 현재 페이지 기준으로 페이지네이션 첫번째 버튼 값. (예: 현 페이지 5 => startPage : 1 )
 * - int endPage : 현재 페이지 기준으로 페이지네이션 마지막 버튼 값. (예: 현 페이지 5 => startPage : 10 )
 * 
 * - boolean prev, next
 * 
 * 만약 현재 페이지 기준(예컨대 5)으로 페이지네이션 버튼들이 화면에 구성되어 있을 때,
 * <|1|2|3|4|"5"|6|7|8|9|10|>
 * 1페이지 이전으로 보여줄 리스트가 존재하는지 (prev)
 * 10페이지 이후로 보여줄 리스트가 존재하는지 (next) 여부를 나타낸다.
 * 위 예시에서 만약 board 에 데이터가 10페이지 이상 있다면,
 * prev = fasle, next = true 이다.
 * 
 * - int total : 해당 결과 테이블의 총 개수. ex) 총 n개의 게시물이 등록되어 있습니다.
 * - Criteria criteria : 현 페이지 쪽수(page), 한 페이지에서 보여줄 게시물 개수(amount) 를 가진 객체
 * 
 * [생성자]
 * 현재 페이지 쪽수(criteria.page), 한 페이지에서 보여줄 게시물 개수(criteria.amount) 를 기반으로
 * pagination 버튼으로 뿌릴 데이터를 계산한다.
 * => startPage,endPage,prev,next 를 화면 스크립트에서 사용 가능.
 * 
 */
public class Pagination {
    private int startPage;
    private int endPage;
    private boolean prev,next;

    private int total;
    private Criteria cri;

    // TODO : 다시 증명하고, realEnd 주석 달기.
    // TODO : scale 을 디폴트로 설정하기.
    /**
     * total = select 로 가져온 게시물 개수
     * 
     * 분기 
     * 분기 
     * @param cri
     * @param total
     */
    public Pagination(Criteria cri, int total){
        this.cri = cri;
        this.total = total;

        this.endPage = (int)(Math.ceil(cri.getPage()/10.0)) * 10;
        this.startPage = this.endPage - 9;

        // ??
        int realEnd = (int)(Math.ceil( total * 1.0 ) / cri.getAmount() );
        if( realEnd <this.endPage ) this.endPage = realEnd;

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }

    
}
