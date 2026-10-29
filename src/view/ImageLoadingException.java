package view;

/**
 * An exception, needed to display more clearly the reson behind an image
 * loading failure.
 */
public class ImageLoadingException extends RuntimeException {

	public ImageLoadingException(String message) {
		super(message);
	}

}
