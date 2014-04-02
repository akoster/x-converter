package xcon.example.bridge.textchecker;

public class Word {
    private String value;

    public Word(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.toLowerCase().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Word))
            return false;
        Word other = (Word) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        }
        else if (!value.equalsIgnoreCase(other.value))
            return false;
        return true;
    }
}