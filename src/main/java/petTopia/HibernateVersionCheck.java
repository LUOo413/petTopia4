package petTopia;

import org.hibernate.Version;

public class HibernateVersionCheck {
	public static void main(String[] args) {
		System.out.println("Hibernate Version: " + Version.getVersionString());
	}
}
