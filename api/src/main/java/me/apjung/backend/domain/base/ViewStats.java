package me.apjung.backend.domain.base;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ViewStats {
    @Column(name = "page_view")
    private Long pageView;

    // TODO: 2020-11-29 unique_view -> unique_visitor
    @Column(name = "unique_view")
    private Long uniqueView;

    public ViewStats() {
        this.pageView = 0L;
        this.uniqueView = 0L;
    }

    public ViewStats(Long pageView, Long uniqueView) {
        this.pageView = pageView;
        this.uniqueView = uniqueView;
    }
}