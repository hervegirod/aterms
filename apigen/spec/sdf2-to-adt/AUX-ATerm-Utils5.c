/* This C code is generated by the AsfSdfCompiler version 1.3 */

#include  "asc-support.h"
static Symbol lf_AUX_ATerm_Utils5_1sym ;
static ATerm lf_AUX_ATerm_Utils5_1 ( ATerm arg1 , ATerm arg2 ) ;
static Symbol ef3sym ;
static funcptr ef3 ;
static Symbol ef4sym ;
static funcptr ef4 ;
static Symbol lf3sym ;
static ATerm lf3 ( ATerm arg1 ) ;
static Symbol ef5sym ;
static funcptr ef5 ;
static Symbol ef1sym ;
static funcptr ef1 ;
static Symbol ef2sym ;
static funcptr ef2 ;
static Symbol lf2sym ;
static ATerm lf2 ( ATerm arg1 ) ;
void register_AUX_ATerm_Utils5 ( ) {
lf_AUX_ATerm_Utils5_1sym = ATmakeSymbol ( "prod(id(\"ATerm-Utils\"),w(\"\"),[ql(\"merge-adts\"),w(\"\"),ql(\"(\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\",\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),no-attrs)"
 , 2 , ATtrue ) ;
ATprotectSymbol ( lf_AUX_ATerm_Utils5_1sym ) ;
lf3sym = ATmakeSymbol ( "listtype(sort(\"CHAR\"))" , 1 , ATtrue ) ;
ATprotectSymbol ( lf3sym ) ;
lf2sym = ATmakeSymbol ( "listtype(sort(\"ATerm\"),ql(\",\"))" , 1 , ATtrue ) ;
ATprotectSymbol ( lf2sym ) ;
register_prod ( ATparse ( "prod(id(\"ATerm-Utils\"),w(\"\"),[ql(\"merge-adts\"),w(\"\"),ql(\"(\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\",\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),no-attrs)" ) , lf_AUX_ATerm_Utils5_1 , lf_AUX_ATerm_Utils5_1sym ) ;
register_prod ( ATparse ( "listtype(sort(\"ATerm\"),ql(\",\"))" ) , lf2 , lf2sym ) ;
register_prod ( ATparse ( "listtype(sort(\"CHAR\"))" ) , lf3 , lf3sym ) ;
}
void resolve_AUX_ATerm_Utils5 ( ) {
ef1 = lookup_func ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[sort(\"ATermList\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),attrs(l(\"{\"),w(\"\"),[l(\"constructor\")],w(\"\"),l(\"}\")))" ) ) ;
ef1sym = lookup_sym ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[sort(\"ATermList\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),attrs(l(\"{\"),w(\"\"),[l(\"constructor\")],w(\"\"),l(\"}\")))" ) ) ;
ef2 = lookup_func ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[ql(\"[\"),w(\"\"),iter-sep(l(\"{\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\",\"),w(\"\"),l(\"}\"),w(\"\"),l(\"*\")),w(\"\"),ql(\"]\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATermList\"),w(\"\"),no-attrs)" ) ) ;
ef2sym = lookup_sym ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[ql(\"[\"),w(\"\"),iter-sep(l(\"{\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\",\"),w(\"\"),l(\"}\"),w(\"\"),l(\"*\")),w(\"\"),ql(\"]\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATermList\"),w(\"\"),no-attrs)" ) ) ;
ef3 = lookup_func ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[sort(\"AFun\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),attrs(l(\"{\"),w(\"\"),[l(\"constructor\")],w(\"\"),l(\"}\")))" ) ) ;
ef3sym = lookup_sym ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[sort(\"AFun\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),attrs(l(\"{\"),w(\"\"),[l(\"constructor\")],w(\"\"),l(\"}\")))" ) ) ;
ef4 = lookup_func ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[sort(\"AFun\"),w(\"\"),ql(\"(\"),w(\"\"),iter-sep(l(\"{\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\",\"),w(\"\"),l(\"}\"),w(\"\"),l(\"+\")),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),attrs(l(\"{\"),w(\"\"),[l(\"constructor\")],w(\"\"),l(\"}\")))" ) ) ;
ef4sym = lookup_sym ( ATreadFromString ( "prod(id(\"ATerm-Syntax\"),w(\"\"),[sort(\"AFun\"),w(\"\"),ql(\"(\"),w(\"\"),iter-sep(l(\"{\"),w(\"\"),sort(\"ATerm\"),w(\"\"),ql(\",\"),w(\"\"),l(\"}\"),w(\"\"),l(\"+\")),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"ATerm\"),w(\"\"),attrs(l(\"{\"),w(\"\"),[l(\"constructor\")],w(\"\"),l(\"}\")))" ) ) ;
ef5 = lookup_func ( ATreadFromString ( "prod(id(\"GEN-LexConsFuncs\"),w(\"\"),[ql(\"afun\"),w(\"\"),ql(\"(\"),w(\"\"),iter(sort(\"CHAR\"),w(\"\"),l(\"*\")),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"AFun\"),w(\"\"),no-attrs)" ) ) ;
ef5sym = lookup_sym ( ATreadFromString ( "prod(id(\"GEN-LexConsFuncs\"),w(\"\"),[ql(\"afun\"),w(\"\"),ql(\"(\"),w(\"\"),iter(sort(\"CHAR\"),w(\"\"),l(\"*\")),w(\"\"),ql(\")\")],w(\"\"),l(\"->\"),w(\"\"),sort(\"AFun\"),w(\"\"),no-attrs)" ) ) ;
}
void init_AUX_ATerm_Utils5 ( ) {
}
ATerm lf_AUX_ATerm_Utils5_1 ( ATerm arg0 , ATerm arg1 ) {
{
ATerm tmp [ 24 ] ;
FUNC_ENTRY ( lf_AUX_ATerm_Utils5_1sym , ATmakeAppl ( lf_AUX_ATerm_Utils5_1sym , arg0 , arg1 ) ) ;
{
ATerm ltmp [ 2 ] ;
lbl_lf_AUX_ATerm_Utils5_1 : ltmp [ 0 ] = arg0 ;
( ltmp [ 1 ] = arg1 ) ;
if ( check_sym ( ltmp [ 0 ] , ef1sym ) ) {
{
ATerm atmp00 = arg_0 ( arg0 ) ;
if ( check_sym ( atmp00 , ef2sym ) ) {
{
ATerm atmp000 = arg_0 ( atmp00 ) ;
if ( check_sym ( atmp000 , lf2sym ) ) {
( tmp [ 0 ] = arg_0 ( atmp000 ) ) ;
{
ATerm atmp000010 ;
ATerm atmp00000 [ 2 ] ;
( atmp00000 [ 0 ] = tmp [ 0 ] ) ;
( atmp00000 [ 1 ] = tmp [ 0 ] ) ;
while ( not_empty_list ( tmp [ 0 ] ) ) {
( atmp000010 = list_head ( tmp [ 0 ] ) ) ;
( tmp [ 0 ] = list_tail ( tmp [ 0 ] ) ) ;
if ( check_sym ( ltmp [ 1 ] , ef1sym ) ) {
{
ATerm atmp10 = arg_0 ( arg1 ) ;
if ( check_sym ( atmp10 , ef2sym ) ) {
{
ATerm atmp100 = arg_0 ( atmp10 ) ;
if ( check_sym ( atmp100 , lf2sym ) ) {
{
ATerm atmp1000 = arg_0 ( atmp100 ) ;
if ( not_empty_list ( atmp1000 ) ) {
( tmp [ 1 ] = list_head ( atmp1000 ) ) ;
( tmp [ 2 ] = list_tail ( atmp1000 ) ) ;
if ( term_equal ( atmp000010 , tmp [ 1 ] ) ) {
( arg1 = make_nf1 ( ef1sym , ( * ef2 ) ( lf2 ( make_list ( tmp [ 2 ] ) ) ) ) ) ;
goto lbl_lf_AUX_ATerm_Utils5_1 ;
}
if ( check_sym ( atmp000010 , ef1sym ) ) {
( tmp [ 3 ] = arg_0 ( atmp000010 ) ) ;
if ( check_sym ( tmp [ 3 ] , ef2sym ) ) {
( tmp [ 4 ] = arg_0 ( tmp [ 3 ] ) ) ;
if ( check_sym ( tmp [ 4 ] , lf2sym ) ) {
( tmp [ 5 ] = arg_0 ( tmp [ 4 ] ) ) ;
if ( not_empty_list ( tmp [ 5 ] ) ) {
( tmp [ 6 ] = list_head ( tmp [ 5 ] ) ) ;
( tmp [ 7 ] = list_tail ( tmp [ 5 ] ) ) ;
if ( not_empty_list ( tmp [ 7 ] ) ) {
( tmp [ 8 ] = list_head ( tmp [ 7 ] ) ) ;
( tmp [ 9 ] = list_tail ( tmp [ 7 ] ) ) ;
if ( is_single_element ( tmp [ 9 ] ) ) {
( tmp [ 10 ] = list_head ( tmp [ 9 ] ) ) ;
if ( check_sym ( tmp [ 6 ] , ef3sym ) ) {
( tmp [ 11 ] = arg_0 ( tmp [ 6 ] ) ) ;
if ( check_sym ( tmp [ 8 ] , ef3sym ) ) {
( tmp [ 12 ] = arg_0 ( tmp [ 8 ] ) ) ;
if ( check_sym ( tmp [ 1 ] , ef1sym ) ) {
( tmp [ 13 ] = arg_0 ( tmp [ 1 ] ) ) ;
if ( check_sym ( tmp [ 13 ] , ef2sym ) ) {
( tmp [ 14 ] = arg_0 ( tmp [ 13 ] ) ) ;
if ( check_sym ( tmp [ 14 ] , lf2sym ) ) {
( tmp [ 15 ] = arg_0 ( tmp [ 14 ] ) ) ;
if ( not_empty_list ( tmp [ 15 ] ) ) {
( tmp [ 16 ] = list_head ( tmp [ 15 ] ) ) ;
( tmp [ 17 ] = list_tail ( tmp [ 15 ] ) ) ;
if ( not_empty_list ( tmp [ 17 ] ) ) {
( tmp [ 18 ] = list_head ( tmp [ 17 ] ) ) ;
( tmp [ 19 ] = list_tail ( tmp [ 17 ] ) ) ;
if ( is_single_element ( tmp [ 19 ] ) ) {
( tmp [ 20 ] = list_head ( tmp [ 19 ] ) ) ;
if ( check_sym ( tmp [ 16 ] , ef3sym ) ) {
( tmp [ 21 ] = arg_0 ( tmp [ 16 ] ) ) ;
if ( check_sym ( tmp [ 18 ] , ef3sym ) ) {
( tmp [ 22 ] = arg_0 ( tmp [ 18 ] ) ) ;
if ( term_equal ( tmp [ 11 ] , tmp [ 21 ] ) ) {
if ( term_equal ( tmp [ 12 ] , tmp [ 22 ] ) ) {
if ( ! term_equal ( tmp [ 10 ] , tmp [ 20 ] ) ) {
( tmp [ 23 ] = lf_AUX_ATerm_Utils5_1 ( make_nf1 ( ef1sym , ( * ef2 ) ( lf2 ( cons ( slice ( atmp00000 [ 0 ] , atmp00000 [ 1 ] ) , cons ( make_list ( atmp000010 ) , cons ( make_list ( make_nf2 ( ef4sym , ( * ef5 ) ( lf3 ( ( ATerm ) ATmakeList ( 10 , char_table [ 99 ] , char_table [ 111 ] , char_table [ 108 ] , char_table [ 108 ] , char_table [ 105 ] , char_table [ 115 ] , char_table [ 115 ] , char_table [ 105 ] , char_table [ 111 ] , char_table [ 110 ] ) ) ) , lf2 ( cons ( make_list ( atmp000010 ) , make_list ( tmp [ 1 ] ) ) ) ) ) , tmp [ 0 ] ) ) ) ) ) ) , make_nf1 ( ef1sym , ( * ef2 ) ( lf2 ( make_list ( tmp [ 2 ] ) ) ) ) ) ) ;
FUNC_EXIT ( tmp [ 23 ] ) ;
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
( atmp00000 [ 1 ] = list_tail ( atmp00000 [ 1 ] ) ) ;
( tmp [ 0 ] = atmp00000 [ 1 ] ) ;
}
}
( tmp [ 0 ] = arg_0 ( atmp000 ) ) ;
}
}
}
}
}
if ( check_sym ( ltmp [ 1 ] , ef1sym ) ) {
{
ATerm atmp10 = arg_0 ( arg1 ) ;
if ( check_sym ( atmp10 , ef2sym ) ) {
{
ATerm atmp100 = arg_0 ( atmp10 ) ;
if ( check_sym ( atmp100 , lf2sym ) ) {
{
ATerm atmp1000 = arg_0 ( atmp100 ) ;
if ( ! not_empty_list ( atmp1000 ) ) {
FUNC_EXIT ( ltmp [ 0 ] ) ;
}
}
}
}
}
}
}
if ( check_sym ( ltmp [ 0 ] , ef1sym ) ) {
{
ATerm atmp00 = arg_0 ( arg0 ) ;
if ( check_sym ( atmp00 , ef2sym ) ) {
{
ATerm atmp000 = arg_0 ( atmp00 ) ;
if ( check_sym ( atmp000 , lf2sym ) ) {
{
ATerm atmp0000 = arg_0 ( atmp000 ) ;
if ( check_sym ( ltmp [ 1 ] , ef1sym ) ) {
{
ATerm atmp10 = arg_0 ( arg1 ) ;
if ( check_sym ( atmp10 , ef2sym ) ) {
{
ATerm atmp100 = arg_0 ( atmp10 ) ;
if ( check_sym ( atmp100 , lf2sym ) ) {
{
ATerm atmp1000 = arg_0 ( atmp100 ) ;
if ( not_empty_list ( atmp1000 ) ) {
( tmp [ 0 ] = list_head ( atmp1000 ) ) ;
( tmp [ 1 ] = list_tail ( atmp1000 ) ) ;
( arg0 = make_nf1 ( ef1sym , ( * ef2 ) ( lf2 ( cons ( make_list ( atmp0000 ) , make_list ( tmp [ 0 ] ) ) ) ) ) ) ;
( arg1 = make_nf1 ( ef1sym , ( * ef2 ) ( lf2 ( make_list ( tmp [ 1 ] ) ) ) ) ) ;
goto lbl_lf_AUX_ATerm_Utils5_1 ;
}
}
}
}
}
}
}
}
}
}
}
}
}
FUNC_EXIT ( make_nf2 ( lf_AUX_ATerm_Utils5_1sym , ltmp [ 0 ] , ltmp [ 1 ] ) ) ;
}
}
}
ATerm lf2 ( ATerm arg0 ) {
CONS_ENTRY ( lf2sym , ATmakeAppl ( lf2sym , arg0 ) ) ;
CONS_EXIT ( make_nf1 ( lf2sym , arg0 ) ) ;
}
ATerm lf3 ( ATerm arg0 ) {
CONS_ENTRY ( lf3sym , ATmakeAppl ( lf3sym , arg0 ) ) ;
CONS_EXIT ( make_nf1 ( lf3sym , arg0 ) ) ;
}

