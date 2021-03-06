/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.sand.accsys.infrastructure.idl.thrift.struct;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-03-21")
public class FileMateBatchResponse implements org.apache.thrift.TBase<FileMateBatchResponse, FileMateBatchResponse._Fields>, java.io.Serializable, Cloneable, Comparable<FileMateBatchResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FileMateBatchResponse");

  private static final org.apache.thrift.protocol.TField PRODUCT_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("productInfo", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField RESPONSE_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("responseInfo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField BATCH_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("batchNo", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FileMateBatchResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FileMateBatchResponseTupleSchemeFactory());
  }

  public ProductInfo productInfo; // required
  public ResponseInfo responseInfo; // required
  public String batchNo; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PRODUCT_INFO((short)1, "productInfo"),
    RESPONSE_INFO((short)2, "responseInfo"),
    BATCH_NO((short)3, "batchNo");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PRODUCT_INFO
          return PRODUCT_INFO;
        case 2: // RESPONSE_INFO
          return RESPONSE_INFO;
        case 3: // BATCH_NO
          return BATCH_NO;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PRODUCT_INFO, new org.apache.thrift.meta_data.FieldMetaData("productInfo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ProductInfo.class)));
    tmpMap.put(_Fields.RESPONSE_INFO, new org.apache.thrift.meta_data.FieldMetaData("responseInfo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ResponseInfo.class)));
    tmpMap.put(_Fields.BATCH_NO, new org.apache.thrift.meta_data.FieldMetaData("batchNo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FileMateBatchResponse.class, metaDataMap);
  }

  public FileMateBatchResponse() {
  }

  public FileMateBatchResponse(
    ProductInfo productInfo,
    ResponseInfo responseInfo,
    String batchNo)
  {
    this();
    this.productInfo = productInfo;
    this.responseInfo = responseInfo;
    this.batchNo = batchNo;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FileMateBatchResponse(FileMateBatchResponse other) {
    if (other.isSetProductInfo()) {
      this.productInfo = new ProductInfo(other.productInfo);
    }
    if (other.isSetResponseInfo()) {
      this.responseInfo = new ResponseInfo(other.responseInfo);
    }
    if (other.isSetBatchNo()) {
      this.batchNo = other.batchNo;
    }
  }

  public FileMateBatchResponse deepCopy() {
    return new FileMateBatchResponse(this);
  }

  @Override
  public void clear() {
    this.productInfo = null;
    this.responseInfo = null;
    this.batchNo = null;
  }

  public ProductInfo getProductInfo() {
    return this.productInfo;
  }

  public FileMateBatchResponse setProductInfo(ProductInfo productInfo) {
    this.productInfo = productInfo;
    return this;
  }

  public void unsetProductInfo() {
    this.productInfo = null;
  }

  /** Returns true if field productInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetProductInfo() {
    return this.productInfo != null;
  }

  public void setProductInfoIsSet(boolean value) {
    if (!value) {
      this.productInfo = null;
    }
  }

  public ResponseInfo getResponseInfo() {
    return this.responseInfo;
  }

  public FileMateBatchResponse setResponseInfo(ResponseInfo responseInfo) {
    this.responseInfo = responseInfo;
    return this;
  }

  public void unsetResponseInfo() {
    this.responseInfo = null;
  }

  /** Returns true if field responseInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetResponseInfo() {
    return this.responseInfo != null;
  }

  public void setResponseInfoIsSet(boolean value) {
    if (!value) {
      this.responseInfo = null;
    }
  }

  public String getBatchNo() {
    return this.batchNo;
  }

  public FileMateBatchResponse setBatchNo(String batchNo) {
    this.batchNo = batchNo;
    return this;
  }

  public void unsetBatchNo() {
    this.batchNo = null;
  }

  /** Returns true if field batchNo is set (has been assigned a value) and false otherwise */
  public boolean isSetBatchNo() {
    return this.batchNo != null;
  }

  public void setBatchNoIsSet(boolean value) {
    if (!value) {
      this.batchNo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PRODUCT_INFO:
      if (value == null) {
        unsetProductInfo();
      } else {
        setProductInfo((ProductInfo)value);
      }
      break;

    case RESPONSE_INFO:
      if (value == null) {
        unsetResponseInfo();
      } else {
        setResponseInfo((ResponseInfo)value);
      }
      break;

    case BATCH_NO:
      if (value == null) {
        unsetBatchNo();
      } else {
        setBatchNo((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PRODUCT_INFO:
      return getProductInfo();

    case RESPONSE_INFO:
      return getResponseInfo();

    case BATCH_NO:
      return getBatchNo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PRODUCT_INFO:
      return isSetProductInfo();
    case RESPONSE_INFO:
      return isSetResponseInfo();
    case BATCH_NO:
      return isSetBatchNo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FileMateBatchResponse)
      return this.equals((FileMateBatchResponse)that);
    return false;
  }

  public boolean equals(FileMateBatchResponse that) {
    if (that == null)
      return false;

    boolean this_present_productInfo = true && this.isSetProductInfo();
    boolean that_present_productInfo = true && that.isSetProductInfo();
    if (this_present_productInfo || that_present_productInfo) {
      if (!(this_present_productInfo && that_present_productInfo))
        return false;
      if (!this.productInfo.equals(that.productInfo))
        return false;
    }

    boolean this_present_responseInfo = true && this.isSetResponseInfo();
    boolean that_present_responseInfo = true && that.isSetResponseInfo();
    if (this_present_responseInfo || that_present_responseInfo) {
      if (!(this_present_responseInfo && that_present_responseInfo))
        return false;
      if (!this.responseInfo.equals(that.responseInfo))
        return false;
    }

    boolean this_present_batchNo = true && this.isSetBatchNo();
    boolean that_present_batchNo = true && that.isSetBatchNo();
    if (this_present_batchNo || that_present_batchNo) {
      if (!(this_present_batchNo && that_present_batchNo))
        return false;
      if (!this.batchNo.equals(that.batchNo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_productInfo = true && (isSetProductInfo());
    list.add(present_productInfo);
    if (present_productInfo)
      list.add(productInfo);

    boolean present_responseInfo = true && (isSetResponseInfo());
    list.add(present_responseInfo);
    if (present_responseInfo)
      list.add(responseInfo);

    boolean present_batchNo = true && (isSetBatchNo());
    list.add(present_batchNo);
    if (present_batchNo)
      list.add(batchNo);

    return list.hashCode();
  }

  @Override
  public int compareTo(FileMateBatchResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetProductInfo()).compareTo(other.isSetProductInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProductInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.productInfo, other.productInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResponseInfo()).compareTo(other.isSetResponseInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResponseInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.responseInfo, other.responseInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBatchNo()).compareTo(other.isSetBatchNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBatchNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.batchNo, other.batchNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FileMateBatchResponse(");
    boolean first = true;

    sb.append("productInfo:");
    if (this.productInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.productInfo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("responseInfo:");
    if (this.responseInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.responseInfo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("batchNo:");
    if (this.batchNo == null) {
      sb.append("null");
    } else {
      sb.append(this.batchNo);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    if (productInfo == null) {
      throw new TProtocolException("Required field 'productInfo' was not present! Struct: " + toString());
    }
    if (responseInfo == null) {
      throw new TProtocolException("Required field 'responseInfo' was not present! Struct: " + toString());
    }
    if (batchNo == null) {
      throw new TProtocolException("Required field 'batchNo' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (productInfo != null) {
      productInfo.validate();
    }
    if (responseInfo != null) {
      responseInfo.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FileMateBatchResponseStandardSchemeFactory implements SchemeFactory {
    public FileMateBatchResponseStandardScheme getScheme() {
      return new FileMateBatchResponseStandardScheme();
    }
  }

  private static class FileMateBatchResponseStandardScheme extends StandardScheme<FileMateBatchResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FileMateBatchResponse struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PRODUCT_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.productInfo = new ProductInfo();
              struct.productInfo.read(iprot);
              struct.setProductInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RESPONSE_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.responseInfo = new ResponseInfo();
              struct.responseInfo.read(iprot);
              struct.setResponseInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BATCH_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.batchNo = iprot.readString();
              struct.setBatchNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, FileMateBatchResponse struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.productInfo != null) {
        oprot.writeFieldBegin(PRODUCT_INFO_FIELD_DESC);
        struct.productInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.responseInfo != null) {
        oprot.writeFieldBegin(RESPONSE_INFO_FIELD_DESC);
        struct.responseInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.batchNo != null) {
        oprot.writeFieldBegin(BATCH_NO_FIELD_DESC);
        oprot.writeString(struct.batchNo);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FileMateBatchResponseTupleSchemeFactory implements SchemeFactory {
    public FileMateBatchResponseTupleScheme getScheme() {
      return new FileMateBatchResponseTupleScheme();
    }
  }

  private static class FileMateBatchResponseTupleScheme extends TupleScheme<FileMateBatchResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FileMateBatchResponse struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.productInfo.write(oprot);
      struct.responseInfo.write(oprot);
      oprot.writeString(struct.batchNo);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FileMateBatchResponse struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.productInfo = new ProductInfo();
      struct.productInfo.read(iprot);
      struct.setProductInfoIsSet(true);
      struct.responseInfo = new ResponseInfo();
      struct.responseInfo.read(iprot);
      struct.setResponseInfoIsSet(true);
      struct.batchNo = iprot.readString();
      struct.setBatchNoIsSet(true);
    }
  }

}

