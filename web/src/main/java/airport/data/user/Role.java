package airport.data.user;

/**
 * Роли пользователей в системе.
 */
public enum Role {
	ADMIN {
		@Override
		public String toString() {
			return "Admin";
		}
	}, MEMBER {
		@Override
		public String toString() {
			return "Member";
		}
	};
	
	public boolean isAdmin() {
		return this.equals(Role.ADMIN);
	}
	
	public boolean isMember() {
		return this.equals(Role.MEMBER);
	}
}
