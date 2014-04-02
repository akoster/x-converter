package xcon.stackoverflow;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <pre>
 * class PieceFactory {
 *    @SuppressWarnings("rawtypes")
 *    public Piece createPiece(String pieceType) throws Throwable{
 *        Class pieceClass = Class.forName(pieceType);
 *        Piece piece = (Piece) pieceClass.newInstance();
 *
 * return piece;
 *     }
 * }
 </pre>
 * I'm not all used to handling exceptions yet therefore I'm just throwing them, but everywhere
 * I use a method that uses this factory it tells me I have to throw exceptions like throwable.
 * For example, in one of my classes I have a method that instantiates a lot of objects using
 * the method that uses the factory. I can use the method in that class by just throwing the
 * exception, however it won't work if I try to pass a reference to that class to another class
 * and then use the method from there. Then it forces me to try catch the exception.
 *
 * I probably don't need a factory but it seemed interesting and I'd like to try to use patterns.
 * The reason I created the factory was that I have 6 subclasses of Piece and I wan't to use a
 * method to instantiate them by passing the type of subclass I want as an argument to the method.
 *
 * http://stackoverflow.com/questions/4681257/is-there-anything-wrong-with-my-factory-class/4682573#4682573
 */
class PieceFactoryExample {

	static enum PieceFactory {
		ROOK {
			Piece create() {
				return new Rook();
			}
		};
		abstract Piece create();
	}

	public Piece createPiece(String pieceType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class pieceClass = Class.forName(pieceType);
        Piece piece = (Piece) pieceClass.newInstance();

         return piece;       
     }
	
	static class Field {
		char line;
		int row;

		public Field(char line, int row) {
			this.line = line;
			this.row = row;
		}

		public String toString() {
			return "" + line + row;
		}
	}

	static interface Piece {

		void moveTo(Field field);

		List<Field> possibleMoves();
	}

	static abstract class AbstractPiece implements Piece {

		Field field;

		public void moveTo(Field field) {
			this.field = field;
		}
	}

	static class Rook extends AbstractPiece {

		public List<Field> possibleMoves() {

			List<Field> moves = new ArrayList<Field>();
			for (char line = 'a'; line <= 'h'; line++) {
				if (line != field.line) {
					moves.add(new Field(line, field.row));
				}
			}
			for (char row = 1; row <= 8; row++) {
				if (row != field.row) {
					moves.add(new Field(field.line, row));
				}
			}
			return moves;
		}
	}

	public static void main(String[] args) {

		Piece pawn = PieceFactory.ROOK.create();
		Field field = new Field('c', 3);
		pawn.moveTo(field);
		List<Field> moves = pawn.possibleMoves();
		System.out.println("Possible moves from " + field);
		for (Field move : moves) {
			System.out.println(move);
		}
	}
	
	class NullPiece implements Piece {
		public void moveTo(Field field) {
			throw new UnsupportedOperationException();
		}
		public List<Field> possibleMoves() {
			throw new UnsupportedOperationException();
		}
    }
}
