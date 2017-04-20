package io.openshift.appdev.missioncontrol.core.api;


import io.openshift.appdev.missioncontrol.base.identity.Identity;

/**
 * DSL builder for creating {@link Projectile} objects.  Responsible for
 * validating state before calling upon the {@link ForkProjectileBuilder#build()}
 * operation.  The following properties are required:
 * <p>
 * <ul>
 * <li>sourceGitHubRepo</li>
 * <li>gitHubIdentity</li>
 * </ul>
 * <p>
 * Each property's valid value and purpose is documented in its setter method.
 *
 * @author <a href="mailto:alr@redhat.com">Andrew Lee Rubinger</a>
 */
public class ProjectileBuilder {

    private ProjectileBuilder() {
        // No external instances
    }

    ProjectileBuilder(Identity gitHubIdentity, Identity openShiftIdentity, String openShiftProjectName) {
        this.gitHubIdentity = gitHubIdentity;
        this.openShiftIdentity = openShiftIdentity;
        this.openShiftProjectName = openShiftProjectName;
    }

    private Identity gitHubIdentity;

    private Identity openShiftIdentity;

    /**
     * the name of OpenShift project to create.
     */
    private String openShiftProjectName;

    /**
     * Creates and returns a new instance with uninitialized values
     *
     * @return a new instance of the {@link ProjectileBuilder}
     */
    public static ProjectileBuilder newInstance() {
        return new ProjectileBuilder();
    }

    /**
     * Sets the name of the OpenShift project to create. By default, the name is derived from
     * the GitHub repository to fork. Optional.
     *
     * @param openShiftProjectName
     * @return This builder
     */
    public ProjectileBuilder openShiftProjectName(final String openShiftProjectName) {
        this.openShiftProjectName = openShiftProjectName;
        return this;
    }

    /*
     * Builder methods
     */

    /**
     * Sets the GitHub access token we have obtained from the user as part of
     * the OAuth process. Required.
     *
     * @param identity
     * @return This builder
     */
    public ProjectileBuilder gitHubIdentity(final Identity identity) {
        this.gitHubIdentity = identity;
        return this;
    }

    /**
     * Sets the Openshift access token we have obtained from the user as part of
     * the OAuth process. Required.
     *
     * @param identity
     * @return This builder
     */
    public ProjectileBuilder openShiftIdentity(final Identity identity) {
        this.openShiftIdentity = identity;
        return this;
    }

    /**
     * @return the GitHub access token we have obtained from the user as part of
     * the OAuth process
     */
    public Identity getGitHubIdentity() {
        return this.gitHubIdentity;
    }

    public Identity getOpenShiftIdentity() {
        return openShiftIdentity;
    }

    /**
     * @return The name to use in creating the new OpenShift project
     */
    public String getOpenShiftProjectName() {
        return openShiftProjectName;
    }

    public CreateProjectileBuilder createType() {
        return new CreateProjectileBuilder(getGitHubIdentity(), getOpenShiftIdentity(), getOpenShiftProjectName());
    }

    public ForkProjectileBuilder forkType() {
        return new ForkProjectileBuilder(getGitHubIdentity(), getOpenShiftIdentity(), getOpenShiftProjectName());
    }


    /**
     * Ensures the specified value is not null or empty, else throws
     * an {@link IllegalArgumentException} citing the specified name
     * (which is also required ;) )
     *
     * @param name
     * @param value
     * @throws IllegalStateException
     */
    static void checkSpecified(final String name,
                               final Object value) throws IllegalStateException {
        assert name != null && !name.isEmpty() : "name is required";

        if (value == null || (value instanceof String && ((String)value).isEmpty())) {
            throw new IllegalStateException(name + " must be specified");
        }
    }

    void build(ProjectileBuilder builder) {
        ProjectileBuilder.checkSpecified("gitHubIdentity", this.gitHubIdentity);
        ProjectileBuilder.checkSpecified("openShiftIdentity", this.openShiftIdentity);
        // Default the openshiftProjectName if need be
        try {
            ProjectileBuilder.checkSpecified("openshiftProjectName", this.openShiftProjectName);
        } catch (final IllegalStateException ise) {
            this.openShiftProjectName(builder.createDefaultProjectName());
        }
    }

    String createDefaultProjectName() {
        throw new IllegalStateException("needs to be called on specific type");
    }

}
