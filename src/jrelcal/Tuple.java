package jrelcal;

public interface Tuple<S extends Comparable<S>, T extends Comparable<T>> extends Comparable<Pair<S, T>>  {
	public S getFirst();
	public T getSecond();
}
