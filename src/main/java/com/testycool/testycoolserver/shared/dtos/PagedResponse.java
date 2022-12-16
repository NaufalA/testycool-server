package com.testycool.testycoolserver.shared.dtos;

import org.springframework.data.domain.Page;

public class PagedResponse<T> {
    private final Integer page;
    private final Integer size;
    private final Integer fetchedSize;
    private final Long totalSize;
    private final Integer totalPage;
    private final Iterable<T> content;

    public PagedResponse(Page<T> pagedResult) {
        this.page = pagedResult.getNumber();
        this.size = pagedResult.getSize();
        this.fetchedSize = pagedResult.getNumberOfElements();
        this.totalSize = pagedResult.getTotalElements();
        this.totalPage = pagedResult.getTotalPages();
        this.content = pagedResult.getContent();
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getFetchedSize() {
        return fetchedSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Iterable<T> getContent() {
        return content;
    }
}
