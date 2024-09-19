const core = require('@actions/core');
const github = require('@actions/github');

try {
    // Get the pattern input (e.g., 'Closes #123')
    const patternInput = core.getInput('pattern');
    const issueLinkPattern = new RegExp(patternInput);

    // Get the PR body from the event payload
    const prBody = github.context.payload.pull_request.body;

    if (!prBody) {
        core.setFailed('Pull request body is empty. Please provide a description.');
        return;
    }

    // Check if the PR body contains an issue link
    if (!issueLinkPattern.test(prBody)) {
        core.setFailed(`Pull request must link to an issue using the pattern '${patternInput}' (e.g., 'Closes #123').`);
    } else {
        core.info('Pull request contains a linked issue.');
    }
} catch (error) {
    core.setFailed(`Error checking PR body: ${error.message}`);
}