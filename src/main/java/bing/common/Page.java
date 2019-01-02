package bing.common;

import lombok.Data;

import java.util.List;

/**
 * Page
 *
 * @author: IceWee
 * @date: 2019.1.2
 */
@Data
public class Page<M> {

    private int pageNo = 1;

    private int pageSize = 10;

    private int totalCount;

    private volatile int totalPages;

    private volatile int index;

    private List<M> data;

    public int getIndex() {
        index = (pageNo - 1) * pageSize;
        return index;
    }

    public int getTotalPages() {
        if (totalCount % pageSize == 0) {
            totalPages = totalCount / pageSize;
        } else {
            totalPages = (totalCount / pageSize) + 1;
        }
        return totalPages;
    }

}
