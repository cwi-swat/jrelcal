package jrelcal;

public class Pair<S extends Comparable<S>, T extends Comparable<T>> implements
		Comparable<Pair<S, T>> {
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

	public boolean equals(Object o) {
		Pair pair = (Pair) o;
		return pair.getFirst().equals(getFirst())
				&& pair.getSecond().equals(getSecond());
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
