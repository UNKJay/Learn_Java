package hust.cs.javacourse.homework7.p2;

class TwoTuple<T1 extends Comparable<T1> , T2 extends Comparable<T2>> implements Comparable<TwoTuple<T1,T2>> {
    private T1 first;
    private T2 second;

    TwoTuple(T1 first , T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    @Override
    public int compareTo(TwoTuple<T1,T2> o) {
        int firstResult = first.compareTo(o.getFirst());
        return firstResult != 0 ? firstResult : second.compareTo(o.getSecond());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TwoTuple) {
            return first.equals(((TwoTuple<T1,T2>) obj).first) && second.equals(((TwoTuple<T1,T2>) obj).second);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "," + second.toString() + ")";
    }
}
