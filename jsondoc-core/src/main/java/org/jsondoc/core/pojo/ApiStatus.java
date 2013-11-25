package org.jsondoc.core.pojo;

/**
 * @author Daniel Ostermeier
 */
public enum ApiStatus {

    /**
     * Indicates that the API status is currently undefined. This is the default.
     */
    UNDEFINED,
    /**
     * Indicates that this API is proposed, but not yet agreed upon.  The details
     * are likely to change.
     */
    PROPOSED,
    /**
     * Indicates that this API is currently under development.  The details may
     * change during implementation.
     */
    UNDER_DEVELOPMENT,
    /**
     * Indicates that this API is final.
     */
    RELEASED,
    /**
     * Indicates that this API is for internal usage only.
     */
    INTERNAL
}
