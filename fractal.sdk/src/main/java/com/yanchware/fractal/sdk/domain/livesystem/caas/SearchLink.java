package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.ComponentLink;
import com.yanchware.fractal.sdk.domain.values.ComponentId;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Builds the link from a consumer to a search component (e.g. {@link CaaSOpenSearch}).
 * <p>
 * The link carries the settings <code>access</code> (required) and <code>indexPrefix</code> (optional;
 * when omitted the search component scopes the consumer to <code>&lt;consumerComponentId&gt;-</code>).
 * The search component does the work: it creates the consumer's user and role, scoped to
 * <code>&lt;indexPrefix&gt;*</code>, and places the credentials in a Kubernetes Secret in the
 * consumer's namespace.
 */
public final class SearchLink {
  public static final String ACCESS_KEY = "access";
  public static final String INDEX_PREFIX_KEY = "indexPrefix";

  private static final Pattern INDEX_PREFIX_PATTERN = Pattern.compile("^[a-z0-9._-]+$");
  private static final String ACCESS_IS_NULL = "[SearchLink Validation] Access has not been defined and it is required";
  private static final String INDEX_PREFIX_NOT_VALID = "[SearchLink Validation] Index prefix '%s' is illegal. It must not be blank and may only contain lowercase letters, digits, '-', '_' and '.' (no wildcards)";

  private SearchLink() {
  }

  /**
   * Builds the link to the search component with the given id.
   *
   * @param target      id of the search component (or of a local reference to one)
   * @param access      access granted to the consumer
   * @param indexPrefix prefix of the indices the consumer may use; <code>null</code> for the default
   * @return the link
   * @throws IllegalArgumentException when access is missing or the index prefix is not valid
   */
  public static ComponentLink to(ComponentId target, SearchAccess access, String indexPrefix) {
    if (target == null) {
      throw new IllegalArgumentException("[SearchLink Validation] Target search component has not been defined and it is required");
    }

    if (access == null) {
      throw new IllegalArgumentException(ACCESS_IS_NULL);
    }

    Map<String, Object> settings = new HashMap<>();
    settings.put(ACCESS_KEY, access.getValue());

    if (indexPrefix != null) {
      requireValidIndexPrefix(indexPrefix);
      settings.put(INDEX_PREFIX_KEY, indexPrefix);
    }

    return ComponentLink.builder()
        .withComponentId(target)
        .withSettings(settings)
        .build();
  }

  static void requireValidIndexPrefix(String indexPrefix) {
    if (isBlank(indexPrefix) || !INDEX_PREFIX_PATTERN.matcher(indexPrefix).matches()) {
      throw new IllegalArgumentException(String.format(INDEX_PREFIX_NOT_VALID, indexPrefix));
    }
  }
}
