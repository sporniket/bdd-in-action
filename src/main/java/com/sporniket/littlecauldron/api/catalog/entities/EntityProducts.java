package com.sporniket.littlecauldron.api.catalog.entities;

@javax.persistence.Entity
@javax.persistence.Table(name = "products")
public class EntityProducts {
  @javax.persistence.Column(name = "id")
  @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
  @javax.persistence.Id
  private Integer id ;
  public Integer getId() { return id ;} 
  public void setId(Integer value) { this.id = value ;} 
  public EntityProducts withId(Integer value) { this.id = value ; return this ;} 

  @javax.persistence.Column(name = "label")
  private String label ;
  public String getLabel() { return label ;} 
  public void setLabel(String value) { this.label = value ;} 
  public EntityProducts withLabel(String value) { this.label = value ; return this ;} 

  @javax.persistence.Column(name = "sku")
  private String sku ;
  public String getSku() { return sku ;} 
  public void setSku(String value) { this.sku = value ;} 
  public EntityProducts withSku(String value) { this.sku = value ; return this ;} 


  public boolean equals(Object obj) {
    if (null == this.id) return super.equals(obj) ;
    if (null == obj) return false ;
    if (!(obj instanceof EntityProducts)) return false ;

    EntityProducts _id = (EntityProducts) obj ;
    if (!java.util.Objects.equals(this.id, _id.getId())) return false ;
    return true;
  }

  public int hashCode() {
    if (null == this.id) return super.hashCode() ;
    return java.util.Objects.hash(id) ;
  }
}

