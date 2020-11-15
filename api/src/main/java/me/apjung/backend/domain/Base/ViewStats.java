package me.apjung.backend.domain.Base;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ViewStats {
    @Column(name = "page_view")
    private Long pageView;

    @Column(name = "unique_view")
    private Long uniqueView;

    public ViewStats() {
        this.pageView = 0L;
        this.uniqueView = 0L;
    }
}
