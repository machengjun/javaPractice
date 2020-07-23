package com.example.saas.bean;

/**
 * @author 马成军
 **/
public class DsCheckResult {
    private boolean isTenant = false;
    private boolean isPlatform = false;
    private String className;
    private String methodName;

    public DsCheckResult(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public DsCheckResult(boolean isTenant, boolean isPlatform, String className) {
        this.isTenant = isTenant;
        this.isPlatform = isPlatform;
        this.className = className;
    }

    public DsCheckResult(boolean isTenant, boolean isPlatform, String className, String methodName) {
        this.isTenant = isTenant;
        this.isPlatform = isPlatform;
        this.className = className;
        this.methodName = methodName;
    }

    public boolean isTenant() {
        return this.isTenant;
    }

    public boolean isPlatform() {
        return this.isPlatform;
    }

    public String getClassName() {
        return this.className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setTenant(final boolean isTenant) {
        this.isTenant = isTenant;
    }

    public void setPlatform(final boolean isPlatform) {
        this.isPlatform = isPlatform;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DsCheckResult)) {
            return false;
        } else {
            DsCheckResult other = (DsCheckResult) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isTenant() != other.isTenant()) {
                return false;
            } else if (this.isPlatform() != other.isPlatform()) {
                return false;
            } else {
                label40:
                {
                    Object this$className = this.getClassName();
                    Object other$className = other.getClassName();
                    if (this$className == null) {
                        if (other$className == null) {
                            break label40;
                        }
                    } else if (this$className.equals(other$className)) {
                        break label40;
                    }

                    return false;
                }

                Object this$methodName = this.getMethodName();
                Object other$methodName = other.getMethodName();
                if (this$methodName == null) {
                    if (other$methodName != null) {
                        return false;
                    }
                } else if (!this$methodName.equals(other$methodName)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DsCheckResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + (this.isTenant() ? 79 : 97);
        result = result * 59 + (this.isPlatform() ? 79 : 97);
        Object $className = this.getClassName();
        result = result * 59 + ($className == null ? 43 : $className.hashCode());
        Object $methodName = this.getMethodName();
        result = result * 59 + ($methodName == null ? 43 : $methodName.hashCode());
        return result;
    }

    public String toString() {
        return "DsCheckResult(isTenant=" + this.isTenant() + ", isPlatform=" + this.isPlatform() + ", className=" + this.getClassName() + ", methodName=" + this.getMethodName() + ")";
    }
}
