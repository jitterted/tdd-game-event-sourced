package dev.ted.tddgame.jeslib;

public sealed abstract class Result<SUCCESS, FAILURE> {

    public static <SUCCESS, FAILURE> Result<SUCCESS, FAILURE> success(SUCCESS value) {
        return new SuccessResult<>(value);
    }

    public static <SUCCESS, FAILURE> Result<SUCCESS, FAILURE> failure(FAILURE message) {
        return new FailureResult<>(message);
    }

    public abstract SUCCESS value();

    public abstract boolean isSuccess();

    public boolean isFailure() {
        return !isSuccess();
    }

    public abstract FAILURE failureInfo();


    private static final class SuccessResult<SUCCESS, FAILURE> extends Result<SUCCESS, FAILURE> {
        private final SUCCESS value;

        private SuccessResult(SUCCESS value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public SUCCESS value() {
            return value;
        }

        @Override
        public FAILURE failureInfo() {
            return null;
        }
    }

    private static final class FailureResult<SUCCESS, FAILURE> extends Result<SUCCESS, FAILURE> {
        private final FAILURE failureInfo;

        private FailureResult(FAILURE failureInfo) {
            this.failureInfo = failureInfo;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public SUCCESS value() {
            return null;
        }

        @Override
        public FAILURE failureInfo() {
            return failureInfo;
        }
    }

}
