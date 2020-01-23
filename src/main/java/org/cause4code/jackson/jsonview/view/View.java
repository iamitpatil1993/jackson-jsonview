package org.cause4code.jackson.jsonview.view;

/**
 * This interface defines different Views for different models in application.
 * 
 * @author amipatil
 *
 */
public class View {

	/**
	 * Enclosing type to define user views
	 */
	public static interface UserView {

		/**
		 * View for external users/services
		 */
		public static interface External {
		}

		/**
		 * View for internal services/uses
		 */
		public static interface Internal extends External {
		}
	}
}
