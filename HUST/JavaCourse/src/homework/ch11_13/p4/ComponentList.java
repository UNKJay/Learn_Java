package homework.ch11_13.p4;

import java.util.ArrayList;

public class ComponentList extends ArrayList<Component> implements Iterator {

    private int position;

    public ComponentList() {
        super();
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < this.size();
    }

    @Override
    public Component next() {
        if (this.hasNext()) {
            return this.get(position++);
        }
        return null;
    }
}
