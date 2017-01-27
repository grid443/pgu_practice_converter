package ru.pgu.practice.csv_to_doc.model;

import java.util.List;

/**
 * Conversion operation state
 */
public class Response {
    /**
     * Result files list
     */
    private final List<String> files;

    /**
     * Operation state
     */
    private final OperationState state;

    public Response(List<String> files) {
        this.files = files;
        this.state = OperationState.SUCCESS;
    }

    public Response(OperationState state) {
        this.files = null;
        this.state = state;
    }

    public List<String> getFiles() {
        return files;
    }

    public OperationState getState() {
        return state;
    }
}
