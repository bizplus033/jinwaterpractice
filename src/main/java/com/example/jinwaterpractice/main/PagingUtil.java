package com.example.jinwaterpractice.main;

import org.springframework.data.domain.Page;

public class PagingUtil<T> {
    private int blockSize;
    private int blockStartNo;
    private int blockEndNo;
    private int totalPage;
    private int pageNo;

    public PagingUtil(Page<T> page) {
        this.totalPage = page.getTotalPages();
        this.pageNo = page.getNumber() + 1;
        this.blockSize = 10;
        this.blockStartNo = ((pageNo -1) / blockSize) * blockSize + 1;
        this.blockEndNo = Math.min((blockStartNo + blockSize - 1), totalPage);
    }
}
