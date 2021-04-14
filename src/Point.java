
// This is a class with generics objects . We need it to save the points of the table which are the available movements for the object that calls it

class Point<T>{

    private final T x ;
    private final T y;

    public Point(T x ,T y){
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return this.x;
    }

    public T getY() {
        return this.y;
    }
}
