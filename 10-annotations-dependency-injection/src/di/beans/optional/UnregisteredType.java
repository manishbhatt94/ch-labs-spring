package di.beans.optional;

/**
 * No beans of this type exist anywhere -- used to demonstrate that even a
 * COLLECTION-typed injection point fails under the default required=true,
 * because "at least one matching element is expected."
 */
public interface UnregisteredType {

}
