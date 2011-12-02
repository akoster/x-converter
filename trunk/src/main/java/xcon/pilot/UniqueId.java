package xcon.pilot;

import java.util.UUID;

public class UniqueId {

	public static void main(String[] args) {
		for (int n = 0; n < 10; n++) {
			UUID uuid = UUID.randomUUID();
			System.out.println(n + " uuid=" + uuid + " hashCode="
					+ uuid.hashCode());
		}
	}
}
