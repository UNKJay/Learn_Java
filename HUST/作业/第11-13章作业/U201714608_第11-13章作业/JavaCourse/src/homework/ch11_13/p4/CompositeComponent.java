package homework.ch11_13.p4;

public class CompositeComponent extends Component {

    protected ComponentList childs;

    public CompositeComponent() {
        super();
        childs = new ComponentList();
    }

    public CompositeComponent(int id, String name, double price) {
        super(id, name, price);
        this.childs = new ComponentList();
    }

    @Override
    public void add(Component component) throws UnsupportedOperationException {
        if (!this.childs.contains(component)){
            this.childs.add(component);
        }
    }

    @Override
    public void remove(Component component) throws UnsupportedOperationException {
        this.childs.remove(component);
    }

    @Override
    public double calcPrice() {
        int sum = 0;
        for (Component e:this.childs
             ) {
            sum += e.calcPrice();
        }
        return sum ;
    }

    @Override
    public Iterator iterator() {
        return new CompositeIterator(this.childs);
    }

    @Override
    public String toString() {
        String s = super.toString();
        s += ("sub-components of " + this.name + ":\n");

        for (Component e : this.childs
             ) {
            s += e.toString();
        }
        return s;
    }
}
