package net.abyssdev.abysslib.builders;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chubbyduck1
 * @param <E>
 */
@Data
public class PageBuilder<E> {

    private final List<E> types;
    private final int amountPerPage;

    public PageBuilder(List<E> types,
                       int amountPerPage) {
        this.types = types;
        this.amountPerPage = amountPerPage;
    }

    public int getMaxPage() {
        return ((this.types.size() / this.amountPerPage) - 1);
    }

    public boolean hasPage(int page) {
        return !(page > getMaxPage());
    }

    public List<E> getPage(int page) {
        List<E> pageTypes = new ArrayList<>();
        if (page > getMaxPage() + 1) {
            return pageTypes;
        }

        for (int i = 0; i < this.amountPerPage; i++) {
            int current = i + (page * this.amountPerPage);

            if (current >= this.types.size()) {
                break;
            }

            pageTypes.add(this.types.get(current));
        }

        return pageTypes;
    }

}
