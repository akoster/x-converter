package xcon.pilot;

import java.util.ArrayList;
import java.util.List;

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
