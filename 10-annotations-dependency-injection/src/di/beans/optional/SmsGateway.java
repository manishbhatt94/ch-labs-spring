package di.beans.optional;

/**
 * Interface with NO bean implementation registered anywhere in this package --
 * deliberately left unsatisfied for the required=false demo.
 */
public interface SmsGateway {

	void send(String message);

}
