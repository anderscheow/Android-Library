package io.github.anderscheow.library.base.live.view_model;

import android.app.Application;
import android.databinding.ObservableField;

@Deprecated
public abstract class PaginatedAndroidViewModel<T> extends BaseAndroidViewModel<T> {

    public final ObservableField<Long> totalOfElements = new ObservableField<>();

    private int pageNumber = 0;
    private boolean hasNextPage = false;

    public PaginatedAndroidViewModel(Application context) {
        super(context);
    }

    @Override
    public void onRefresh() {
        setPageNumber(0);
    }

    protected void setTotalOfElements(long totalOfElements) {
        this.totalOfElements.set(totalOfElements);
        this.totalOfElements.notifyChange();
    }

    protected int getPageNumber() {
        return pageNumber;
    }

    protected void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    protected void incrementPageNumber() {
        this.pageNumber = pageNumber + 1;
    }

    protected boolean hasNextPage() {
        return hasNextPage;
    }

    protected void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
