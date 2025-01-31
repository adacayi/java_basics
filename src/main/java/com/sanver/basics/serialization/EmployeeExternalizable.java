package com.sanver.basics.serialization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeExternalizable implements Externalizable {
    private String firstName;
    private String lastName;
    private transient double salary; // Even transient is specified here, since writeExternal writes salary it will be serialized.

    /**
     * Serializes only first name and salary
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(firstName);
        out.writeDouble(salary);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setFirstName(in.readUTF());
        setSalary(in.readDouble());
    }
}
