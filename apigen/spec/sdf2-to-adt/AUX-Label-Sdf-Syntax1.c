/* This C code is generated by the AsfSdfCompiler version 1.3 */

#include  "asc-support.h"
static Symbol lf_AUX_Label_Sdf_Syntax1_1sym ;
static ATerm lf_AUX_Label_Sdf_Syntax1_1 ( ATerm arg1 , ATerm arg2 ) ;
static Symbol lf_AUX_Label_Sdf_Syntax1_2sym ;
static ATerm lf_AUX_Label_Sdf_Syntax1_2 ( ATerm arg1 ) ;
static Symbol lf_AUX_Label_Sdf_Syntax1_3sym ;
static ATerm lf_AUX_Label_Sdf_Syntax1_3 ( ) ;
static Symbol lf_AUX_Label_Sdf_Syntax1_4sym ;
static ATerm lf_AUX_Label_Sdf_Syntax1_4 ( ) ;
static Symbol lf_AUX_Label_Sdf_Syntax1_5sym ;
static ATerm lf_AUX_Label_Sdf_Syntax1_5 ( ) ;
void register_AUX_Label_Sdf_Syntax1 ( ) {
lf_AUX_Label_Sdf_Syntax1_1sym = ATmakeSymbol ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[sort(\"Literal\"),w(\"\"),ql(\":\"),w(\"\"),sort(\"Symbol\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Symbol\"),w(\"\"),no-attrs)"
 , 2 , ATtrue ) ;
ATprotectSymbol ( lf_AUX_Label_Sdf_Syntax1_1sym ) ;
lf_AUX_Label_Sdf_Syntax1_2sym = ATmakeSymbol ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"cons\"),w(\"\"),ql(\"(\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" , 1 , ATtrue ) ;
ATprotectSymbol ( lf_AUX_Label_Sdf_Syntax1_2sym ) ;
lf_AUX_Label_Sdf_Syntax1_3sym = ATmakeSymbol ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"constructor\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" , 0 , ATtrue ) ;
ATprotectSymbol ( lf_AUX_Label_Sdf_Syntax1_3sym ) ;
lf_AUX_Label_Sdf_Syntax1_4sym = ATmakeSymbol ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"memo\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" , 0 , ATtrue ) ;
ATprotectSymbol ( lf_AUX_Label_Sdf_Syntax1_4sym ) ;
lf_AUX_Label_Sdf_Syntax1_5sym = ATmakeSymbol ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"traverse\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" , 0 , ATtrue ) ;
ATprotectSymbol ( lf_AUX_Label_Sdf_Syntax1_5sym ) ;
register_prod ( ATparse ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[sort(\"Literal\"),w(\"\"),ql(\":\"),w(\"\"),sort(\"Symbol\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Symbol\"),w(\"\"),no-attrs)" ) , lf_AUX_Label_Sdf_Syntax1_1 , lf_AUX_Label_Sdf_Syntax1_1sym ) ;
register_prod ( ATparse ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"cons\"),w(\"\"),ql(\"(\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" ) , lf_AUX_Label_Sdf_Syntax1_2 , lf_AUX_Label_Sdf_Syntax1_2sym ) ;
register_prod ( ATparse ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"constructor\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" ) , lf_AUX_Label_Sdf_Syntax1_3 , lf_AUX_Label_Sdf_Syntax1_3sym ) ;
register_prod ( ATparse ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"memo\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" ) , lf_AUX_Label_Sdf_Syntax1_4 , lf_AUX_Label_Sdf_Syntax1_4sym ) ;
register_prod ( ATparse ( "prod(id(\"Label-Sdf-Syntax\"),w(\"\"),[ql(\"traverse\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"Attribute\"),w(\"\"),no-attrs)" ) , lf_AUX_Label_Sdf_Syntax1_5 , lf_AUX_Label_Sdf_Syntax1_5sym ) ;
}
void resolve_AUX_Label_Sdf_Syntax1 ( ) {
}
void init_AUX_Label_Sdf_Syntax1 ( ) {
}
ATerm lf_AUX_Label_Sdf_Syntax1_5 ( ) {
CONS_ENTRY ( lf_AUX_Label_Sdf_Syntax1_5sym , ATmakeAppl0 ( lf_AUX_Label_Sdf_Syntax1_5sym ) ) ;
CONS_EXIT ( make_nf0 ( lf_AUX_Label_Sdf_Syntax1_5sym ) ) ;
}
ATerm lf_AUX_Label_Sdf_Syntax1_4 ( ) {
CONS_ENTRY ( lf_AUX_Label_Sdf_Syntax1_4sym , ATmakeAppl0 ( lf_AUX_Label_Sdf_Syntax1_4sym ) ) ;
CONS_EXIT ( make_nf0 ( lf_AUX_Label_Sdf_Syntax1_4sym ) ) ;
}
ATerm lf_AUX_Label_Sdf_Syntax1_3 ( ) {
CONS_ENTRY ( lf_AUX_Label_Sdf_Syntax1_3sym , ATmakeAppl0 ( lf_AUX_Label_Sdf_Syntax1_3sym ) ) ;
CONS_EXIT ( make_nf0 ( lf_AUX_Label_Sdf_Syntax1_3sym ) ) ;
}
ATerm lf_AUX_Label_Sdf_Syntax1_2 ( ATerm arg0 ) {
CONS_ENTRY ( lf_AUX_Label_Sdf_Syntax1_2sym , ATmakeAppl ( lf_AUX_Label_Sdf_Syntax1_2sym , arg0 ) ) ;
CONS_EXIT ( make_nf1 ( lf_AUX_Label_Sdf_Syntax1_2sym , arg0 ) ) ;
}
ATerm lf_AUX_Label_Sdf_Syntax1_1 ( ATerm arg0 , ATerm arg1 ) {
CONS_ENTRY ( lf_AUX_Label_Sdf_Syntax1_1sym , ATmakeAppl ( lf_AUX_Label_Sdf_Syntax1_1sym , arg0 , arg1 ) ) ;
CONS_EXIT ( make_nf2 ( lf_AUX_Label_Sdf_Syntax1_1sym , arg0 , arg1 ) ) ;
}

