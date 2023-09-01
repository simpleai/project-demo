package com.xiaoruiit.project.demo.interfaces.common;

/**
 * @author hanxiaorui
 * @date 2023/8/31
 */

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -2471506313020616980L;
    public static final int DEFAULT_PAGE_SIZE = 10;
    private int pageNum;
    private int pageSize;
    private int totalCount;
    private List<T> list;

    public PageResult() {
    }

    public PageResult(int pageNum) {
        this.pageNum = pageNum;
        this.pageSize = 10;
        this.list = Collections.emptyList();
    }

    public PageResult(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = Collections.emptyList();
    }

    public PageResult(int pageNum, int pageSize, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PageResult(int pageNum, int pageSize, int totalCount, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;
    }

    public int getPageCount() {
        return this.pageSize == 0 ? 1 : (int)Math.ceil((double)this.totalCount * 1.0 / (double)this.pageSize);
    }

    public int getNext() {
        int pc = this.getPageCount();
        return this.pageNum < pc ? this.pageNum + 1 : pc;
    }

    public boolean getHasNext() {
        return this.pageNum < this.getPageCount();
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResult)) {
            return false;
        } else {
            PageResult<?> other = (PageResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getPageNum() != other.getPageNum()) {
                return false;
            } else if (this.getPageSize() != other.getPageSize()) {
                return false;
            } else if (this.getTotalCount() != other.getTotalCount()) {
                return false;
            } else {
                Object this$list = this.getList();
                Object other$list = other.getList();
                if (this$list == null) {
                    if (other$list != null) {
                        return false;
                    }
                } else if (!this$list.equals(other$list)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageResult;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getPageNum();
        result = result * 59 + this.getPageSize();
        result = result * 59 + this.getTotalCount();
        Object $list = this.getList();
        result = result * 59 + ($list == null ? 43 : $list.hashCode());
        return result;
    }

    public String toString() {
        return "PageResult(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ", totalCount=" + this.getTotalCount() + ", list=" + this.getList() + ")";
    }
}

