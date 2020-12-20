package me.apjung.backend.domain.base;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ViewStats {
    @Column(name = "page_view")
    private Long pageView;

    @Column(name = "unique_visitor")
    private Long uniqueVisitor;

    public ViewStats() {
        this.pageView = 0L;
        this.uniqueVisitor = 0L;
    }

    public ViewStats(Long pageView, Long uniqueVisitor) {
        this.pageView = pageView;
        this.uniqueVisitor = uniqueVisitor;
    }

    public void visit() {
        this.pageView++;
    }

    public void firstVisit() {
        this.uniqueVisitor++;
        visit();
    }
}