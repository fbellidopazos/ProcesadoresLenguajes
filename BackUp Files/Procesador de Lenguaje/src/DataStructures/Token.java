package DataStructures;

/**
 * Implementacion de la clase Pair(Con nombre Token), obtenida a traves de
 * https://www.techiedelight.com/implement-Token-class-java/
 */
public class Token<U, V> {
	public final U first; // first field of a Token
	public final V second; // second field of a Token

	// Constructs a new Token with specified values
	private Token(U first, V second) {
		this.first = first;
		this.second = second;
	}

	@Override
	// Checks specified object is "equal to" current object or not
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Token<?, ?> Token = (Token<?, ?>) o;

		// call equals() method of the underlying objects
		if (!first.equals(Token.first))
			return false;
		return second.equals(Token.second);
	}

	@Override
	// Computes hash code for an object to support hash tables
	public int hashCode() {
		// use hash codes of the underlying objects
		return 31 * first.hashCode() + second.hashCode();
	}

	@Override
	public String toString() {
		return "<" + first + ", " + second + ">";
	}

	// Factory method for creating a Typed Token immutable instance
	public static <U, V> Token<U, V> of(U a, V b) {
		// calls private constructor
		return new Token<>(a, b);
	}
}