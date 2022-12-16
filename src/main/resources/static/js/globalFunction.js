function replaceNull(val){
    return val===null ?
        "":
        typeof val === "string"?
        val.trim() : val;
}

function replaceNullDecimal(val){
    return val===null?0:+val;
}
