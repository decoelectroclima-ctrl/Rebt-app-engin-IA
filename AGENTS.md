# AI Coding Instructions for Future Releases

Please adhere strictly to the following configuration and publishing guidelines for this project:

## 1. Package Name / Application ID Constraint
*   **Package Name/Application ID**: Must ALWAYS be exactly `com.aistudio.enginia.pwtvzc`.
*   **Why**: This application is already configured and uploaded to the Google Play Console under this ID. Changing the `applicationId` in `app/build.gradle.kts` will break updates and result in submission failures.

## 2. Versioning Restrictions
*   **Version Code (`versionCode`)**: The current version code uploaded and recognized is **22**.
*   **Increment Requirement**: Any subsequent updates or build corrections must use a `versionCode` strictly greater than the last used version.
*   **Updating Scheme**:
*   Set `versionCode` in `/app/build.gradle.kts` to `23` or higher for the next release.
*   Increment `versionName` accordingly (e.g., `"23.0"`).
