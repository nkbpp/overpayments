package ru.pfr.overpayments.model.annotations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Округление чисел типа Double до 2х знаков после запятой
 * применяется в DTO
 * пример: @JsonSerialize(using = OkrugSerializer.class)
 */
public class OkrugSerializer  extends StdSerializer<Double> {

    protected OkrugSerializer(Class<Double> t) {
        super(t);
    }

    protected OkrugSerializer() {
        this(null);
    }

    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(aDouble != null ? new DecimalFormat("#0.00").format(aDouble).replace(",",".") : "");
    }

}
