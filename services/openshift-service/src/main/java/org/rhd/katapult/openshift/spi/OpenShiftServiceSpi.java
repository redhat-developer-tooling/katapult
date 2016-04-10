package org.rhd.katapult.openshift.spi;

import org.rhd.katapult.openshift.api.OpenShiftProject;
import org.rhd.katapult.openshift.api.OpenShiftService;

/**
 * Defines the service provider interface for implementations of {@link OpenShiftService}
 * that we won't expose in the API but need for testing or other purposes
 *
 * @author <a href="mailto:alr@redhat.com">Andrew Lee Rubinger</a>
 */
public interface OpenShiftServiceSpi extends OpenShiftService {

    /**
     * Deletes the specified, required project
     *
     * @param project
     * @return If the operation resulted in a deletion
     * @throws IllegalArgumentException If the project is not specified
     */
    boolean deleteProject(OpenShiftProject project) throws IllegalArgumentException;

}