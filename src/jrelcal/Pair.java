package jrelcal;

public class Pair<S extends Comparable<S>, T extends Comparable<T>> implements Tuple<S, T> {
	private S first;

	private T second;

	public Pair(S first, T second) {
		this.first = first;
		this.second = second;
	}

	public S getFirst() {
		return first;
	}

	public T getSecond() {
		return second;
	}

	public void setFirst(S s) {
		first = s;
	}

	public void setSecond(T t) {
		second = t;
	}

	public Pair<T, S> swap() {
		return new Pair<T, S>(second, first);
	}

	@Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((first == null) ? 0 : first.hashCode());
        result = PRIME * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
      //  if (!super.equals(obj))
       //     return false;
        if (getClass() != obj.getClass())
            return false;
        final Pair other = (Pair)obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second == null) {
            if (other.second != null)
                return false;
        } else if (!second.equals(other.second))
            return false;
        return true;
    }

	public String toString() {
		return "<" + getFirst().toString() + ", " + getSecond().toString()
				+ ">";
	}

	public int compareTo(Pair<S, T> pair) {
		int n = getFirst().compareTo(pair.getFirst());
		if (n == 0)
			return getSecond().compareTo(pair.getSecond());
		return n;
	}

}
