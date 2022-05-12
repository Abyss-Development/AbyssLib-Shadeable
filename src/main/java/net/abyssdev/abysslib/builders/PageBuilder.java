package net.abyssdev.abysslib.builders;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
        return (types.size() / amountPerPage) - 1;
    }

    public boolean hasPage(int page) {
        return !(page > getMaxPage() + 1);
    }

    public List<E> getPage(int page) {
        List<E> pageTypes = new ArrayList<>();
        if (page > getMaxPage() + 1) return pageTypes;

        for (int i = 0; i < amountPerPage; i++) {
            int current = i + (page * amountPerPage);

            if (current >= types.size()) break;

            pageTypes.add(types.get(current));
        }

        return pageTypes;
    }

}