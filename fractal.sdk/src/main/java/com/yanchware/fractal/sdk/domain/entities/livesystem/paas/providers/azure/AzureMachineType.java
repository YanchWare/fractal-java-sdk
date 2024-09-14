package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureMachineType extends ExtendableEnum<AzureMachineType> {

  public static final AzureMachineType BASIC_A0 = fromString("Basic_A0");
  public static final AzureMachineType BASIC_A1 = fromString("Basic_A1");
  public static final AzureMachineType BASIC_A2 = fromString("Basic_A2");
  public static final AzureMachineType BASIC_A3 = fromString("Basic_A3");
  public static final AzureMachineType BASIC_A4 = fromString("Basic_A4");
  public static final AzureMachineType STANDARD_A0 = fromString("Standard_A0");
  public static final AzureMachineType STANDARD_A1 = fromString("Standard_A1");
  public static final AzureMachineType STANDARD_A1_V2 = fromString("Standard_A1_v2");
  public static final AzureMachineType STANDARD_A2 = fromString("Standard_A2");
  public static final AzureMachineType STANDARD_A2M_V2 = fromString("Standard_A2m_v2");
  public static final AzureMachineType STANDARD_A2_V2 = fromString("Standard_A2_v2");
  public static final AzureMachineType STANDARD_A3 = fromString("Standard_A3");
  public static final AzureMachineType STANDARD_A4 = fromString("Standard_A4");
  public static final AzureMachineType STANDARD_A4M_V2 = fromString("Standard_A4m_v2");
  public static final AzureMachineType STANDARD_A4_V2 = fromString("Standard_A4_v2");
  public static final AzureMachineType STANDARD_A5 = fromString("Standard_A5");
  public static final AzureMachineType STANDARD_A6 = fromString("Standard_A6");
  public static final AzureMachineType STANDARD_A7 = fromString("Standard_A7");
  public static final AzureMachineType STANDARD_A8M_V2 = fromString("Standard_A8m_v2");
  public static final AzureMachineType STANDARD_A8_V2 = fromString("Standard_A8_v2");
  public static final AzureMachineType STANDARD_B12MS = fromString("Standard_B12ms");
  public static final AzureMachineType STANDARD_B16MS = fromString("Standard_B16ms");
  public static final AzureMachineType STANDARD_B1LS = fromString("Standard_B1ls");
  public static final AzureMachineType STANDARD_B1MS = fromString("Standard_B1ms");
  public static final AzureMachineType STANDARD_B1S = fromString("Standard_B1s");
  public static final AzureMachineType STANDARD_B20MS = fromString("Standard_B20ms");
  public static final AzureMachineType STANDARD_B2MS = fromString("Standard_B2ms");
  public static final AzureMachineType STANDARD_B2S = fromString("Standard_B2s");
  public static final AzureMachineType STANDARD_B4MS = fromString("Standard_B4ms");
  public static final AzureMachineType STANDARD_B8MS = fromString("Standard_B8ms");
  public static final AzureMachineType STANDARD_D1 = fromString("Standard_D1");
  public static final AzureMachineType STANDARD_D11 = fromString("Standard_D11");
  public static final AzureMachineType STANDARD_D11_V2 = fromString("Standard_D11_v2");
  public static final AzureMachineType STANDARD_D11_V2_PROMO = fromString("Standard_D11_v2_Promo");
  public static final AzureMachineType STANDARD_D12 = fromString("Standard_D12");
  public static final AzureMachineType STANDARD_D12_V2 = fromString("Standard_D12_v2");
  public static final AzureMachineType STANDARD_D12_V2_PROMO = fromString("Standard_D12_v2_Promo");
  public static final AzureMachineType STANDARD_D13 = fromString("Standard_D13");
  public static final AzureMachineType STANDARD_D13_V2 = fromString("Standard_D13_v2");
  public static final AzureMachineType STANDARD_D13_V2_PROMO = fromString("Standard_D13_v2_Promo");
  public static final AzureMachineType STANDARD_D14 = fromString("Standard_D14");
  public static final AzureMachineType STANDARD_D14_V2 = fromString("Standard_D14_v2");
  public static final AzureMachineType STANDARD_D14_V2_PROMO = fromString("Standard_D14_v2_Promo");
  public static final AzureMachineType STANDARD_D15_V2 = fromString("Standard_D15_v2");
  public static final AzureMachineType STANDARD_D16ADS_V5 = fromString("Standard_D16ads_v5");
  public static final AzureMachineType STANDARD_D16AS_V4 = fromString("Standard_D16as_v4");
  public static final AzureMachineType STANDARD_D16AS_V5 = fromString("Standard_D16as_v5");
  public static final AzureMachineType STANDARD_D16A_V4 = fromString("Standard_D16a_v4");
  public static final AzureMachineType STANDARD_D16DS_V4 = fromString("Standard_D16ds_v4");
  public static final AzureMachineType STANDARD_D16DS_V5 = fromString("Standard_D16ds_v5");
  public static final AzureMachineType STANDARD_D16D_V4 = fromString("Standard_D16d_v4");
  public static final AzureMachineType STANDARD_D16D_V5 = fromString("Standard_D16d_v5");
  public static final AzureMachineType STANDARD_D16PDS_V5 = fromString("Standard_D16pds_v5");
  public static final AzureMachineType STANDARD_D16PLDS_V5 = fromString("Standard_D16plds_v5");
  public static final AzureMachineType STANDARD_D16PLS_V5 = fromString("Standard_D16pls_v5");
  public static final AzureMachineType STANDARD_D16PS_V5 = fromString("Standard_D16ps_v5");
  public static final AzureMachineType STANDARD_D16S_V3 = fromString("Standard_D16s_v3");
  public static final AzureMachineType STANDARD_D16S_V4 = fromString("Standard_D16s_v4");
  public static final AzureMachineType STANDARD_D16S_V5 = fromString("Standard_D16s_v5");
  public static final AzureMachineType STANDARD_D16_V3 = fromString("Standard_D16_v3");
  public static final AzureMachineType STANDARD_D16_V4 = fromString("Standard_D16_v4");
  public static final AzureMachineType STANDARD_D16_V5 = fromString("Standard_D16_v5");
  public static final AzureMachineType STANDARD_D1_V2 = fromString("Standard_D1_v2");
  public static final AzureMachineType STANDARD_D2 = fromString("Standard_D2");
  public static final AzureMachineType STANDARD_D2ADS_V5 = fromString("Standard_D2ads_v5");
  public static final AzureMachineType STANDARD_D2AS_V4 = fromString("Standard_D2as_v4");
  public static final AzureMachineType STANDARD_D2AS_V5 = fromString("Standard_D2as_v5");
  public static final AzureMachineType STANDARD_D2A_V4 = fromString("Standard_D2a_v4");
  public static final AzureMachineType STANDARD_D2DS_V4 = fromString("Standard_D2ds_v4");
  public static final AzureMachineType STANDARD_D2DS_V5 = fromString("Standard_D2ds_v5");
  public static final AzureMachineType STANDARD_D2D_V4 = fromString("Standard_D2d_v4");
  public static final AzureMachineType STANDARD_D2D_V5 = fromString("Standard_D2d_v5");
  public static final AzureMachineType STANDARD_D2PDS_V5 = fromString("Standard_D2pds_v5");
  public static final AzureMachineType STANDARD_D2PLDS_V5 = fromString("Standard_D2plds_v5");
  public static final AzureMachineType STANDARD_D2PLS_V5 = fromString("Standard_D2pls_v5");
  public static final AzureMachineType STANDARD_D2PS_V5 = fromString("Standard_D2ps_v5");
  public static final AzureMachineType STANDARD_D2S_V3 = fromString("Standard_D2s_v3");
  public static final AzureMachineType STANDARD_D2S_V4 = fromString("Standard_D2s_v4");
  public static final AzureMachineType STANDARD_D2S_V5 = fromString("Standard_D2s_v5");
  public static final AzureMachineType STANDARD_D2_V2 = fromString("Standard_D2_v2");
  public static final AzureMachineType STANDARD_D2_V2_PROMO = fromString("Standard_D2_v2_Promo");
  public static final AzureMachineType STANDARD_D2_V3 = fromString("Standard_D2_v3");
  public static final AzureMachineType STANDARD_D2_V4 = fromString("Standard_D2_v4");
  public static final AzureMachineType STANDARD_D2_V5 = fromString("Standard_D2_v5");
  public static final AzureMachineType STANDARD_D3 = fromString("Standard_D3");
  public static final AzureMachineType STANDARD_D32ADS_V5 = fromString("Standard_D32ads_v5");
  public static final AzureMachineType STANDARD_D32AS_V4 = fromString("Standard_D32as_v4");
  public static final AzureMachineType STANDARD_D32AS_V5 = fromString("Standard_D32as_v5");
  public static final AzureMachineType STANDARD_D32A_V4 = fromString("Standard_D32a_v4");
  public static final AzureMachineType STANDARD_D32DS_V4 = fromString("Standard_D32ds_v4");
  public static final AzureMachineType STANDARD_D32DS_V5 = fromString("Standard_D32ds_v5");
  public static final AzureMachineType STANDARD_D32D_V4 = fromString("Standard_D32d_v4");
  public static final AzureMachineType STANDARD_D32D_V5 = fromString("Standard_D32d_v5");
  public static final AzureMachineType STANDARD_D32PDS_V5 = fromString("Standard_D32pds_v5");
  public static final AzureMachineType STANDARD_D32PLDS_V5 = fromString("Standard_D32plds_v5");
  public static final AzureMachineType STANDARD_D32PLS_V5 = fromString("Standard_D32pls_v5");
  public static final AzureMachineType STANDARD_D32PS_V5 = fromString("Standard_D32ps_v5");
  public static final AzureMachineType STANDARD_D32S_V3 = fromString("Standard_D32s_v3");
  public static final AzureMachineType STANDARD_D32S_V4 = fromString("Standard_D32s_v4");
  public static final AzureMachineType STANDARD_D32S_V5 = fromString("Standard_D32s_v5");
  public static final AzureMachineType STANDARD_D32_V3 = fromString("Standard_D32_v3");
  public static final AzureMachineType STANDARD_D32_V4 = fromString("Standard_D32_v4");
  public static final AzureMachineType STANDARD_D32_V5 = fromString("Standard_D32_v5");
  public static final AzureMachineType STANDARD_D3_V2 = fromString("Standard_D3_v2");
  public static final AzureMachineType STANDARD_D3_V2_PROMO = fromString("Standard_D3_v2_Promo");
  public static final AzureMachineType STANDARD_D4 = fromString("Standard_D4");
  public static final AzureMachineType STANDARD_D48ADS_V5 = fromString("Standard_D48ads_v5");
  public static final AzureMachineType STANDARD_D48AS_V4 = fromString("Standard_D48as_v4");
  public static final AzureMachineType STANDARD_D48AS_V5 = fromString("Standard_D48as_v5");
  public static final AzureMachineType STANDARD_D48A_V4 = fromString("Standard_D48a_v4");
  public static final AzureMachineType STANDARD_D48DS_V4 = fromString("Standard_D48ds_v4");
  public static final AzureMachineType STANDARD_D48DS_V5 = fromString("Standard_D48ds_v5");
  public static final AzureMachineType STANDARD_D48D_V4 = fromString("Standard_D48d_v4");
  public static final AzureMachineType STANDARD_D48D_V5 = fromString("Standard_D48d_v5");
  public static final AzureMachineType STANDARD_D48PDS_V5 = fromString("Standard_D48pds_v5");
  public static final AzureMachineType STANDARD_D48PLDS_V5 = fromString("Standard_D48plds_v5");
  public static final AzureMachineType STANDARD_D48PLS_V5 = fromString("Standard_D48pls_v5");
  public static final AzureMachineType STANDARD_D48PS_V5 = fromString("Standard_D48ps_v5");
  public static final AzureMachineType STANDARD_D48S_V3 = fromString("Standard_D48s_v3");
  public static final AzureMachineType STANDARD_D48S_V4 = fromString("Standard_D48s_v4");
  public static final AzureMachineType STANDARD_D48S_V5 = fromString("Standard_D48s_v5");
  public static final AzureMachineType STANDARD_D48_V3 = fromString("Standard_D48_v3");
  public static final AzureMachineType STANDARD_D48_V4 = fromString("Standard_D48_v4");
  public static final AzureMachineType STANDARD_D48_V5 = fromString("Standard_D48_v5");
  public static final AzureMachineType STANDARD_D4ADS_V5 = fromString("Standard_D4ads_v5");
  public static final AzureMachineType STANDARD_D4AS_V4 = fromString("Standard_D4as_v4");
  public static final AzureMachineType STANDARD_D4AS_V5 = fromString("Standard_D4as_v5");
  public static final AzureMachineType STANDARD_D4A_V4 = fromString("Standard_D4a_v4");
  public static final AzureMachineType STANDARD_D4DS_V4 = fromString("Standard_D4ds_v4");
  public static final AzureMachineType STANDARD_D4DS_V5 = fromString("Standard_D4ds_v5");
  public static final AzureMachineType STANDARD_D4D_V4 = fromString("Standard_D4d_v4");
  public static final AzureMachineType STANDARD_D4D_V5 = fromString("Standard_D4d_v5");
  public static final AzureMachineType STANDARD_D4PDS_V5 = fromString("Standard_D4pds_v5");
  public static final AzureMachineType STANDARD_D4PLDS_V5 = fromString("Standard_D4plds_v5");
  public static final AzureMachineType STANDARD_D4PLS_V5 = fromString("Standard_D4pls_v5");
  public static final AzureMachineType STANDARD_D4PS_V5 = fromString("Standard_D4ps_v5");
  public static final AzureMachineType STANDARD_D4S_V3 = fromString("Standard_D4s_v3");
  public static final AzureMachineType STANDARD_D4S_V4 = fromString("Standard_D4s_v4");
  public static final AzureMachineType STANDARD_D4S_V5 = fromString("Standard_D4s_v5");
  public static final AzureMachineType STANDARD_D4_V2 = fromString("Standard_D4_v2");
  public static final AzureMachineType STANDARD_D4_V2_PROMO = fromString("Standard_D4_v2_Promo");
  public static final AzureMachineType STANDARD_D4_V3 = fromString("Standard_D4_v3");
  public static final AzureMachineType STANDARD_D4_V4 = fromString("Standard_D4_v4");
  public static final AzureMachineType STANDARD_D4_V5 = fromString("Standard_D4_v5");
  public static final AzureMachineType STANDARD_D5_V2 = fromString("Standard_D5_v2");
  public static final AzureMachineType STANDARD_D5_V2_PROMO = fromString("Standard_D5_v2_Promo");
  public static final AzureMachineType STANDARD_D64ADS_V5 = fromString("Standard_D64ads_v5");
  public static final AzureMachineType STANDARD_D64AS_V4 = fromString("Standard_D64as_v4");
  public static final AzureMachineType STANDARD_D64AS_V5 = fromString("Standard_D64as_v5");
  public static final AzureMachineType STANDARD_D64A_V4 = fromString("Standard_D64a_v4");
  public static final AzureMachineType STANDARD_D64DS_V4 = fromString("Standard_D64ds_v4");
  public static final AzureMachineType STANDARD_D64DS_V5 = fromString("Standard_D64ds_v5");
  public static final AzureMachineType STANDARD_D64D_V4 = fromString("Standard_D64d_v4");
  public static final AzureMachineType STANDARD_D64D_V5 = fromString("Standard_D64d_v5");
  public static final AzureMachineType STANDARD_D64PDS_V5 = fromString("Standard_D64pds_v5");
  public static final AzureMachineType STANDARD_D64PLDS_V5 = fromString("Standard_D64plds_v5");
  public static final AzureMachineType STANDARD_D64PLS_V5 = fromString("Standard_D64pls_v5");
  public static final AzureMachineType STANDARD_D64PS_V5 = fromString("Standard_D64ps_v5");
  public static final AzureMachineType STANDARD_D64S_V3 = fromString("Standard_D64s_v3");
  public static final AzureMachineType STANDARD_D64S_V4 = fromString("Standard_D64s_v4");
  public static final AzureMachineType STANDARD_D64S_V5 = fromString("Standard_D64s_v5");
  public static final AzureMachineType STANDARD_D64_V3 = fromString("Standard_D64_v3");
  public static final AzureMachineType STANDARD_D64_V4 = fromString("Standard_D64_v4");
  public static final AzureMachineType STANDARD_D64_V5 = fromString("Standard_D64_v5");
  public static final AzureMachineType STANDARD_D8ADS_V5 = fromString("Standard_D8ads_v5");
  public static final AzureMachineType STANDARD_D8AS_V4 = fromString("Standard_D8as_v4");
  public static final AzureMachineType STANDARD_D8AS_V5 = fromString("Standard_D8as_v5");
  public static final AzureMachineType STANDARD_D8A_V4 = fromString("Standard_D8a_v4");
  public static final AzureMachineType STANDARD_D8DS_V4 = fromString("Standard_D8ds_v4");
  public static final AzureMachineType STANDARD_D8DS_V5 = fromString("Standard_D8ds_v5");
  public static final AzureMachineType STANDARD_D8D_V4 = fromString("Standard_D8d_v4");
  public static final AzureMachineType STANDARD_D8D_V5 = fromString("Standard_D8d_v5");
  public static final AzureMachineType STANDARD_D8PDS_V5 = fromString("Standard_D8pds_v5");
  public static final AzureMachineType STANDARD_D8PLDS_V5 = fromString("Standard_D8plds_v5");
  public static final AzureMachineType STANDARD_D8PLS_V5 = fromString("Standard_D8pls_v5");
  public static final AzureMachineType STANDARD_D8PS_V5 = fromString("Standard_D8ps_v5");
  public static final AzureMachineType STANDARD_D8S_V3 = fromString("Standard_D8s_v3");
  public static final AzureMachineType STANDARD_D8S_V4 = fromString("Standard_D8s_v4");
  public static final AzureMachineType STANDARD_D8S_V5 = fromString("Standard_D8s_v5");
  public static final AzureMachineType STANDARD_D8_V3 = fromString("Standard_D8_v3");
  public static final AzureMachineType STANDARD_D8_V4 = fromString("Standard_D8_v4");
  public static final AzureMachineType STANDARD_D8_V5 = fromString("Standard_D8_v5");
  public static final AzureMachineType STANDARD_D96ADS_V5 = fromString("Standard_D96ads_v5");
  public static final AzureMachineType STANDARD_D96AS_V4 = fromString("Standard_D96as_v4");
  public static final AzureMachineType STANDARD_D96AS_V5 = fromString("Standard_D96as_v5");
  public static final AzureMachineType STANDARD_D96A_V4 = fromString("Standard_D96a_v4");
  public static final AzureMachineType STANDARD_D96DS_V5 = fromString("Standard_D96ds_v5");
  public static final AzureMachineType STANDARD_D96D_V5 = fromString("Standard_D96d_v5");
  public static final AzureMachineType STANDARD_D96S_V5 = fromString("Standard_D96s_v5");
  public static final AzureMachineType STANDARD_D96_V5 = fromString("Standard_D96_v5");
  public static final AzureMachineType STANDARD_DC16ADS_V5 = fromString("Standard_DC16ads_v5");
  public static final AzureMachineType STANDARD_DC16AS_V5 = fromString("Standard_DC16as_v5");
  public static final AzureMachineType STANDARD_DC16DS_V3 = fromString("Standard_DC16ds_v3");
  public static final AzureMachineType STANDARD_DC16S_V3 = fromString("Standard_DC16s_v3");
  public static final AzureMachineType STANDARD_DC1DS_V3 = fromString("Standard_DC1ds_v3");
  public static final AzureMachineType STANDARD_DC1S_V2 = fromString("Standard_DC1s_v2");
  public static final AzureMachineType STANDARD_DC1S_V3 = fromString("Standard_DC1s_v3");
  public static final AzureMachineType STANDARD_DC24DS_V3 = fromString("Standard_DC24ds_v3");
  public static final AzureMachineType STANDARD_DC24S_V3 = fromString("Standard_DC24s_v3");
  public static final AzureMachineType STANDARD_DC2ADS_V5 = fromString("Standard_DC2ads_v5");
  public static final AzureMachineType STANDARD_DC2AS_V5 = fromString("Standard_DC2as_v5");
  public static final AzureMachineType STANDARD_DC2DS_V3 = fromString("Standard_DC2ds_v3");
  public static final AzureMachineType STANDARD_DC2S = fromString("Standard_DC2s");
  public static final AzureMachineType STANDARD_DC2S_V2 = fromString("Standard_DC2s_v2");
  public static final AzureMachineType STANDARD_DC2S_V3 = fromString("Standard_DC2s_v3");
  public static final AzureMachineType STANDARD_DC32ADS_V5 = fromString("Standard_DC32ads_v5");
  public static final AzureMachineType STANDARD_DC32AS_V5 = fromString("Standard_DC32as_v5");
  public static final AzureMachineType STANDARD_DC32DS_V3 = fromString("Standard_DC32ds_v3");
  public static final AzureMachineType STANDARD_DC32S_V3 = fromString("Standard_DC32s_v3");
  public static final AzureMachineType STANDARD_DC48ADS_V5 = fromString("Standard_DC48ads_v5");
  public static final AzureMachineType STANDARD_DC48AS_V5 = fromString("Standard_DC48as_v5");
  public static final AzureMachineType STANDARD_DC48DS_V3 = fromString("Standard_DC48ds_v3");
  public static final AzureMachineType STANDARD_DC48S_V3 = fromString("Standard_DC48s_v3");
  public static final AzureMachineType STANDARD_DC4ADS_V5 = fromString("Standard_DC4ads_v5");
  public static final AzureMachineType STANDARD_DC4AS_V5 = fromString("Standard_DC4as_v5");
  public static final AzureMachineType STANDARD_DC4DS_V3 = fromString("Standard_DC4ds_v3");
  public static final AzureMachineType STANDARD_DC4S = fromString("Standard_DC4s");
  public static final AzureMachineType STANDARD_DC4S_V2 = fromString("Standard_DC4s_v2");
  public static final AzureMachineType STANDARD_DC4S_V3 = fromString("Standard_DC4s_v3");
  public static final AzureMachineType STANDARD_DC64ADS_V5 = fromString("Standard_DC64ads_v5");
  public static final AzureMachineType STANDARD_DC64AS_V5 = fromString("Standard_DC64as_v5");
  public static final AzureMachineType STANDARD_DC8ADS_V5 = fromString("Standard_DC8ads_v5");
  public static final AzureMachineType STANDARD_DC8AS_V5 = fromString("Standard_DC8as_v5");
  public static final AzureMachineType STANDARD_DC8DS_V3 = fromString("Standard_DC8ds_v3");
  public static final AzureMachineType STANDARD_DC8S_V3 = fromString("Standard_DC8s_v3");
  public static final AzureMachineType STANDARD_DC8_V2 = fromString("Standard_DC8_v2");
  public static final AzureMachineType STANDARD_DC96ADS_V5 = fromString("Standard_DC96ads_v5");
  public static final AzureMachineType STANDARD_DC96AS_V5 = fromString("Standard_DC96as_v5");
  public static final AzureMachineType STANDARD_DS1 = fromString("Standard_DS1");
  public static final AzureMachineType STANDARD_DS11 = fromString("Standard_DS11");
  public static final AzureMachineType STANDARD_DS11_1_V2 = fromString("Standard_DS11-1_v2");

  public static final AzureMachineType STANDARD_DS11_V2 = fromString("Standard_DS11_v2");
  public static final AzureMachineType STANDARD_DS11_V2_PROMO = fromString("Standard_DS11_v2_Promo");
  public static final AzureMachineType STANDARD_DS12 = fromString("Standard_DS12");
  public static final AzureMachineType STANDARD_DS12_1_V2 = fromString("Standard_DS12-1_v2");

  public static final AzureMachineType STANDARD_DS12_2_V2 = fromString("Standard_DS12-2_v2");

  public static final AzureMachineType STANDARD_DS12_V2 = fromString("Standard_DS12_v2");
  public static final AzureMachineType STANDARD_DS12_V2_PROMO = fromString("Standard_DS12_v2_Promo");
  public static final AzureMachineType STANDARD_DS13 = fromString("Standard_DS13");
  public static final AzureMachineType STANDARD_DS13_2_V2 = fromString("Standard_DS13-2_v2");

  public static final AzureMachineType STANDARD_DS13_4_V2 = fromString("Standard_DS13-4_v2");

  public static final AzureMachineType STANDARD_DS13_V2 = fromString("Standard_DS13_v2");
  public static final AzureMachineType STANDARD_DS13_V2_PROMO = fromString("Standard_DS13_v2_Promo");
  public static final AzureMachineType STANDARD_DS14 = fromString("Standard_DS14");
  public static final AzureMachineType STANDARD_DS14_4_V2 = fromString("Standard_DS14-4_v2");

  public static final AzureMachineType STANDARD_DS14_8_V2 = fromString("Standard_DS14-8_v2");

  public static final AzureMachineType STANDARD_DS14_V2 = fromString("Standard_DS14_v2");
  public static final AzureMachineType STANDARD_DS14_V2_PROMO = fromString("Standard_DS14_v2_Promo");
  public static final AzureMachineType STANDARD_DS15_V2 = fromString("Standard_DS15_v2");
  public static final AzureMachineType STANDARD_DS1_V2 = fromString("Standard_DS1_v2");
  public static final AzureMachineType STANDARD_DS2 = fromString("Standard_DS2");
  public static final AzureMachineType STANDARD_DS2_V2 = fromString("Standard_DS2_v2");
  public static final AzureMachineType STANDARD_DS2_V2_PROMO = fromString("Standard_DS2_v2_Promo");
  public static final AzureMachineType STANDARD_DS3 = fromString("Standard_DS3");
  public static final AzureMachineType STANDARD_DS3_V2 = fromString("Standard_DS3_v2");
  public static final AzureMachineType STANDARD_DS3_V2_PROMO = fromString("Standard_DS3_v2_Promo");
  public static final AzureMachineType STANDARD_DS4 = fromString("Standard_DS4");
  public static final AzureMachineType STANDARD_DS4_V2 = fromString("Standard_DS4_v2");
  public static final AzureMachineType STANDARD_DS4_V2_PROMO = fromString("Standard_DS4_v2_Promo");
  public static final AzureMachineType STANDARD_DS5_V2 = fromString("Standard_DS5_v2");
  public static final AzureMachineType STANDARD_DS5_V2_PROMO = fromString("Standard_DS5_v2_Promo");
  public static final AzureMachineType STANDARD_E104IDS_V5 = fromString("Standard_E104ids_v5");
  public static final AzureMachineType STANDARD_E104ID_V5 = fromString("Standard_E104id_v5");
  public static final AzureMachineType STANDARD_E104IS_V5 = fromString("Standard_E104is_v5");
  public static final AzureMachineType STANDARD_E104I_V5 = fromString("Standard_E104i_v5");
  public static final AzureMachineType STANDARD_E112IADS_V5 = fromString("Standard_E112iads_v5");
  public static final AzureMachineType STANDARD_E112IAS_V5 = fromString("Standard_E112ias_v5");
  public static final AzureMachineType STANDARD_E16_4ADS_V5 = fromString("Standard_E16-4ads_v5");

  public static final AzureMachineType STANDARD_E16_4AS_V4 = fromString("Standard_E16-4as_v4");

  public static final AzureMachineType STANDARD_E16_4AS_V5 = fromString("Standard_E16-4as_v5");

  public static final AzureMachineType STANDARD_E16_4DS_V4 = fromString("Standard_E16-4ds_v4");

  public static final AzureMachineType STANDARD_E16_4DS_V5 = fromString("Standard_E16-4ds_v5");

  public static final AzureMachineType STANDARD_E16_4S_V3 = fromString("Standard_E16-4s_v3");

  public static final AzureMachineType STANDARD_E16_4S_V4 = fromString("Standard_E16-4s_v4");

  public static final AzureMachineType STANDARD_E16_4S_V5 = fromString("Standard_E16-4s_v5");

  public static final AzureMachineType STANDARD_E16_8ADS_V5 = fromString("Standard_E16-8ads_v5");

  public static final AzureMachineType STANDARD_E16_8AS_V4 = fromString("Standard_E16-8as_v4");

  public static final AzureMachineType STANDARD_E16_8AS_V5 = fromString("Standard_E16-8as_v5");

  public static final AzureMachineType STANDARD_E16_8DS_V4 = fromString("Standard_E16-8ds_v4");

  public static final AzureMachineType STANDARD_E16_8DS_V5 = fromString("Standard_E16-8ds_v5");

  public static final AzureMachineType STANDARD_E16_8S_V3 = fromString("Standard_E16-8s_v3");

  public static final AzureMachineType STANDARD_E16_8S_V4 = fromString("Standard_E16-8s_v4");

  public static final AzureMachineType STANDARD_E16_8S_V5 = fromString("Standard_E16-8s_v5");

  public static final AzureMachineType STANDARD_E16ADS_V5 = fromString("Standard_E16ads_v5");
  public static final AzureMachineType STANDARD_E16AS_V4 = fromString("Standard_E16as_v4");
  public static final AzureMachineType STANDARD_E16AS_V5 = fromString("Standard_E16as_v5");
  public static final AzureMachineType STANDARD_E16A_V4 = fromString("Standard_E16a_v4");
  public static final AzureMachineType STANDARD_E16BDS_V5 = fromString("Standard_E16bds_v5");
  public static final AzureMachineType STANDARD_E16BS_V5 = fromString("Standard_E16bs_v5");
  public static final AzureMachineType STANDARD_E16DS_V4 = fromString("Standard_E16ds_v4");
  public static final AzureMachineType STANDARD_E16DS_V5 = fromString("Standard_E16ds_v5");
  public static final AzureMachineType STANDARD_E16D_V4 = fromString("Standard_E16d_v4");
  public static final AzureMachineType STANDARD_E16D_V5 = fromString("Standard_E16d_v5");
  public static final AzureMachineType STANDARD_E16PDS_V5 = fromString("Standard_E16pds_v5");
  public static final AzureMachineType STANDARD_E16PS_V5 = fromString("Standard_E16ps_v5");
  public static final AzureMachineType STANDARD_E16S_V3 = fromString("Standard_E16s_v3");
  public static final AzureMachineType STANDARD_E16S_V4 = fromString("Standard_E16s_v4");
  public static final AzureMachineType STANDARD_E16S_V5 = fromString("Standard_E16s_v5");
  public static final AzureMachineType STANDARD_E16_V3 = fromString("Standard_E16_v3");
  public static final AzureMachineType STANDARD_E16_V4 = fromString("Standard_E16_v4");
  public static final AzureMachineType STANDARD_E16_V5 = fromString("Standard_E16_v5");
  public static final AzureMachineType STANDARD_E20ADS_V5 = fromString("Standard_E20ads_v5");
  public static final AzureMachineType STANDARD_E20AS_V4 = fromString("Standard_E20as_v4");
  public static final AzureMachineType STANDARD_E20AS_V5 = fromString("Standard_E20as_v5");
  public static final AzureMachineType STANDARD_E20A_V4 = fromString("Standard_E20a_v4");
  public static final AzureMachineType STANDARD_E20DS_V4 = fromString("Standard_E20ds_v4");
  public static final AzureMachineType STANDARD_E20DS_V5 = fromString("Standard_E20ds_v5");
  public static final AzureMachineType STANDARD_E20D_V4 = fromString("Standard_E20d_v4");
  public static final AzureMachineType STANDARD_E20D_V5 = fromString("Standard_E20d_v5");
  public static final AzureMachineType STANDARD_E20PDS_V5 = fromString("Standard_E20pds_v5");
  public static final AzureMachineType STANDARD_E20PS_V5 = fromString("Standard_E20ps_v5");
  public static final AzureMachineType STANDARD_E20S_V3 = fromString("Standard_E20s_v3");
  public static final AzureMachineType STANDARD_E20S_V4 = fromString("Standard_E20s_v4");
  public static final AzureMachineType STANDARD_E20S_V5 = fromString("Standard_E20s_v5");
  public static final AzureMachineType STANDARD_E20_V3 = fromString("Standard_E20_v3");
  public static final AzureMachineType STANDARD_E20_V4 = fromString("Standard_E20_v4");
  public static final AzureMachineType STANDARD_E20_V5 = fromString("Standard_E20_v5");
  public static final AzureMachineType STANDARD_E2ADS_V5 = fromString("Standard_E2ads_v5");
  public static final AzureMachineType STANDARD_E2AS_V4 = fromString("Standard_E2as_v4");
  public static final AzureMachineType STANDARD_E2AS_V5 = fromString("Standard_E2as_v5");
  public static final AzureMachineType STANDARD_E2A_V4 = fromString("Standard_E2a_v4");
  public static final AzureMachineType STANDARD_E2BDS_V5 = fromString("Standard_E2bds_v5");
  public static final AzureMachineType STANDARD_E2BS_V5 = fromString("Standard_E2bs_v5");
  public static final AzureMachineType STANDARD_E2DS_V4 = fromString("Standard_E2ds_v4");
  public static final AzureMachineType STANDARD_E2DS_V5 = fromString("Standard_E2ds_v5");
  public static final AzureMachineType STANDARD_E2D_V4 = fromString("Standard_E2d_v4");
  public static final AzureMachineType STANDARD_E2D_V5 = fromString("Standard_E2d_v5");
  public static final AzureMachineType STANDARD_E2PDS_V5 = fromString("Standard_E2pds_v5");
  public static final AzureMachineType STANDARD_E2PS_V5 = fromString("Standard_E2ps_v5");
  public static final AzureMachineType STANDARD_E2S_V3 = fromString("Standard_E2s_v3");
  public static final AzureMachineType STANDARD_E2S_V4 = fromString("Standard_E2s_v4");
  public static final AzureMachineType STANDARD_E2S_V5 = fromString("Standard_E2s_v5");
  public static final AzureMachineType STANDARD_E2_V3 = fromString("Standard_E2_v3");
  public static final AzureMachineType STANDARD_E2_V4 = fromString("Standard_E2_v4");
  public static final AzureMachineType STANDARD_E2_V5 = fromString("Standard_E2_v5");
  public static final AzureMachineType STANDARD_E32_16ADS_V5 = fromString("Standard_E32-16ads_v5");

  public static final AzureMachineType STANDARD_E32_16AS_V4 = fromString("Standard_E32-16as_v4");

  public static final AzureMachineType STANDARD_E32_16AS_V5 = fromString("Standard_E32-16as_v5");

  public static final AzureMachineType STANDARD_E32_16DS_V4 = fromString("Standard_E32-16ds_v4");

  public static final AzureMachineType STANDARD_E32_16DS_V5 = fromString("Standard_E32-16ds_v5");

  public static final AzureMachineType STANDARD_E32_16S_V3 = fromString("Standard_E32-16s_v3");

  public static final AzureMachineType STANDARD_E32_16S_V4 = fromString("Standard_E32-16s_v4");

  public static final AzureMachineType STANDARD_E32_16S_V5 = fromString("Standard_E32-16s_v5");

  public static final AzureMachineType STANDARD_E32_8ADS_V5 = fromString("Standard_E32-8ads_v5");

  public static final AzureMachineType STANDARD_E32_8AS_V4 = fromString("Standard_E32-8as_v4");

  public static final AzureMachineType STANDARD_E32_8AS_V5 = fromString("Standard_E32-8as_v5");

  public static final AzureMachineType STANDARD_E32_8DS_V4 = fromString("Standard_E32-8ds_v4");

  public static final AzureMachineType STANDARD_E32_8DS_V5 = fromString("Standard_E32-8ds_v5");

  public static final AzureMachineType STANDARD_E32_8S_V3 = fromString("Standard_E32-8s_v3");

  public static final AzureMachineType STANDARD_E32_8S_V4 = fromString("Standard_E32-8s_v4");

  public static final AzureMachineType STANDARD_E32_8S_V5 = fromString("Standard_E32-8s_v5");

  public static final AzureMachineType STANDARD_E32ADS_V5 = fromString("Standard_E32ads_v5");
  public static final AzureMachineType STANDARD_E32AS_V4 = fromString("Standard_E32as_v4");
  public static final AzureMachineType STANDARD_E32AS_V5 = fromString("Standard_E32as_v5");
  public static final AzureMachineType STANDARD_E32A_V4 = fromString("Standard_E32a_v4");
  public static final AzureMachineType STANDARD_E32BDS_V5 = fromString("Standard_E32bds_v5");
  public static final AzureMachineType STANDARD_E32BS_V5 = fromString("Standard_E32bs_v5");
  public static final AzureMachineType STANDARD_E32DS_V4 = fromString("Standard_E32ds_v4");
  public static final AzureMachineType STANDARD_E32DS_V5 = fromString("Standard_E32ds_v5");
  public static final AzureMachineType STANDARD_E32D_V4 = fromString("Standard_E32d_v4");
  public static final AzureMachineType STANDARD_E32D_V5 = fromString("Standard_E32d_v5");
  public static final AzureMachineType STANDARD_E32PDS_V5 = fromString("Standard_E32pds_v5");
  public static final AzureMachineType STANDARD_E32PS_V5 = fromString("Standard_E32ps_v5");
  public static final AzureMachineType STANDARD_E32S_V3 = fromString("Standard_E32s_v3");
  public static final AzureMachineType STANDARD_E32S_V4 = fromString("Standard_E32s_v4");
  public static final AzureMachineType STANDARD_E32S_V5 = fromString("Standard_E32s_v5");
  public static final AzureMachineType STANDARD_E32_V3 = fromString("Standard_E32_v3");
  public static final AzureMachineType STANDARD_E32_V4 = fromString("Standard_E32_v4");
  public static final AzureMachineType STANDARD_E32_V5 = fromString("Standard_E32_v5");
  public static final AzureMachineType STANDARD_E4_2ADS_V5 = fromString("Standard_E4-2ads_v5");

  public static final AzureMachineType STANDARD_E4_2AS_V4 = fromString("Standard_E4-2as_v4");

  public static final AzureMachineType STANDARD_E4_2AS_V5 = fromString("Standard_E4-2as_v5");

  public static final AzureMachineType STANDARD_E4_2DS_V4 = fromString("Standard_E4-2ds_v4");

  public static final AzureMachineType STANDARD_E4_2DS_V5 = fromString("Standard_E4-2ds_v5");

  public static final AzureMachineType STANDARD_E4_2S_V3 = fromString("Standard_E4-2s_v3");

  public static final AzureMachineType STANDARD_E4_2S_V4 = fromString("Standard_E4-2s_v4");

  public static final AzureMachineType STANDARD_E4_2S_V5 = fromString("Standard_E4-2s_v5");

  public static final AzureMachineType STANDARD_E48ADS_V5 = fromString("Standard_E48ads_v5");
  public static final AzureMachineType STANDARD_E48AS_V4 = fromString("Standard_E48as_v4");
  public static final AzureMachineType STANDARD_E48AS_V5 = fromString("Standard_E48as_v5");
  public static final AzureMachineType STANDARD_E48A_V4 = fromString("Standard_E48a_v4");
  public static final AzureMachineType STANDARD_E48BDS_V5 = fromString("Standard_E48bds_v5");
  public static final AzureMachineType STANDARD_E48BS_V5 = fromString("Standard_E48bs_v5");
  public static final AzureMachineType STANDARD_E48DS_V4 = fromString("Standard_E48ds_v4");
  public static final AzureMachineType STANDARD_E48DS_V5 = fromString("Standard_E48ds_v5");
  public static final AzureMachineType STANDARD_E48D_V4 = fromString("Standard_E48d_v4");
  public static final AzureMachineType STANDARD_E48D_V5 = fromString("Standard_E48d_v5");
  public static final AzureMachineType STANDARD_E48S_V3 = fromString("Standard_E48s_v3");
  public static final AzureMachineType STANDARD_E48S_V4 = fromString("Standard_E48s_v4");
  public static final AzureMachineType STANDARD_E48S_V5 = fromString("Standard_E48s_v5");
  public static final AzureMachineType STANDARD_E48_V3 = fromString("Standard_E48_v3");
  public static final AzureMachineType STANDARD_E48_V4 = fromString("Standard_E48_v4");
  public static final AzureMachineType STANDARD_E48_V5 = fromString("Standard_E48_v5");
  public static final AzureMachineType STANDARD_E4ADS_V5 = fromString("Standard_E4ads_v5");
  public static final AzureMachineType STANDARD_E4AS_V4 = fromString("Standard_E4as_v4");
  public static final AzureMachineType STANDARD_E4AS_V5 = fromString("Standard_E4as_v5");
  public static final AzureMachineType STANDARD_E4A_V4 = fromString("Standard_E4a_v4");
  public static final AzureMachineType STANDARD_E4BDS_V5 = fromString("Standard_E4bds_v5");
  public static final AzureMachineType STANDARD_E4BS_V5 = fromString("Standard_E4bs_v5");
  public static final AzureMachineType STANDARD_E4DS_V4 = fromString("Standard_E4ds_v4");
  public static final AzureMachineType STANDARD_E4DS_V5 = fromString("Standard_E4ds_v5");
  public static final AzureMachineType STANDARD_E4D_V4 = fromString("Standard_E4d_v4");
  public static final AzureMachineType STANDARD_E4D_V5 = fromString("Standard_E4d_v5");
  public static final AzureMachineType STANDARD_E4PDS_V5 = fromString("Standard_E4pds_v5");
  public static final AzureMachineType STANDARD_E4PS_V5 = fromString("Standard_E4ps_v5");
  public static final AzureMachineType STANDARD_E4S_V3 = fromString("Standard_E4s_v3");
  public static final AzureMachineType STANDARD_E4S_V4 = fromString("Standard_E4s_v4");
  public static final AzureMachineType STANDARD_E4S_V5 = fromString("Standard_E4s_v5");
  public static final AzureMachineType STANDARD_E4_V3 = fromString("Standard_E4_v3");
  public static final AzureMachineType STANDARD_E4_V4 = fromString("Standard_E4_v4");
  public static final AzureMachineType STANDARD_E4_V5 = fromString("Standard_E4_v5");
  public static final AzureMachineType STANDARD_E64_16ADS_V5 = fromString("Standard_E64-16ads_v5");

  public static final AzureMachineType STANDARD_E64_16AS_V4 = fromString("Standard_E64-16as_v4");

  public static final AzureMachineType STANDARD_E64_16AS_V5 = fromString("Standard_E64-16as_v5");

  public static final AzureMachineType STANDARD_E64_16DS_V4 = fromString("Standard_E64-16ds_v4");

  public static final AzureMachineType STANDARD_E64_16DS_V5 = fromString("Standard_E64-16ds_v5");

  public static final AzureMachineType STANDARD_E64_16S_V3 = fromString("Standard_E64-16s_v3");

  public static final AzureMachineType STANDARD_E64_16S_V4 = fromString("Standard_E64-16s_v4");

  public static final AzureMachineType STANDARD_E64_16S_V5 = fromString("Standard_E64-16s_v5");

  public static final AzureMachineType STANDARD_E64_32ADS_V5 = fromString("Standard_E64-32ads_v5");

  public static final AzureMachineType STANDARD_E64_32AS_V4 = fromString("Standard_E64-32as_v4");

  public static final AzureMachineType STANDARD_E64_32AS_V5 = fromString("Standard_E64-32as_v5");

  public static final AzureMachineType STANDARD_E64_32DS_V4 = fromString("Standard_E64-32ds_v4");

  public static final AzureMachineType STANDARD_E64_32DS_V5 = fromString("Standard_E64-32ds_v5");

  public static final AzureMachineType STANDARD_E64_32S_V3 = fromString("Standard_E64-32s_v3");

  public static final AzureMachineType STANDARD_E64_32S_V4 = fromString("Standard_E64-32s_v4");

  public static final AzureMachineType STANDARD_E64_32S_V5 = fromString("Standard_E64-32s_v5");

  public static final AzureMachineType STANDARD_E64ADS_V5 = fromString("Standard_E64ads_v5");
  public static final AzureMachineType STANDARD_E64AS_V4 = fromString("Standard_E64as_v4");
  public static final AzureMachineType STANDARD_E64AS_V5 = fromString("Standard_E64as_v5");
  public static final AzureMachineType STANDARD_E64A_V4 = fromString("Standard_E64a_v4");
  public static final AzureMachineType STANDARD_E64BDS_V5 = fromString("Standard_E64bds_v5");
  public static final AzureMachineType STANDARD_E64BS_V5 = fromString("Standard_E64bs_v5");
  public static final AzureMachineType STANDARD_E64DS_V4 = fromString("Standard_E64ds_v4");
  public static final AzureMachineType STANDARD_E64DS_V5 = fromString("Standard_E64ds_v5");
  public static final AzureMachineType STANDARD_E64D_V4 = fromString("Standard_E64d_v4");
  public static final AzureMachineType STANDARD_E64D_V5 = fromString("Standard_E64d_v5");
  public static final AzureMachineType STANDARD_E64IS_V3 = fromString("Standard_E64is_v3");
  public static final AzureMachineType STANDARD_E64I_V3 = fromString("Standard_E64i_v3");
  public static final AzureMachineType STANDARD_E64S_V3 = fromString("Standard_E64s_v3");
  public static final AzureMachineType STANDARD_E64S_V4 = fromString("Standard_E64s_v4");
  public static final AzureMachineType STANDARD_E64S_V5 = fromString("Standard_E64s_v5");
  public static final AzureMachineType STANDARD_E64_V3 = fromString("Standard_E64_v3");
  public static final AzureMachineType STANDARD_E64_V4 = fromString("Standard_E64_v4");
  public static final AzureMachineType STANDARD_E64_V5 = fromString("Standard_E64_v5");
  public static final AzureMachineType STANDARD_E8_2ADS_V5 = fromString("Standard_E8-2ads_v5");

  public static final AzureMachineType STANDARD_E8_2AS_V4 = fromString("Standard_E8-2as_v4");

  public static final AzureMachineType STANDARD_E8_2AS_V5 = fromString("Standard_E8-2as_v5");

  public static final AzureMachineType STANDARD_E8_2DS_V4 = fromString("Standard_E8-2ds_v4");

  public static final AzureMachineType STANDARD_E8_2DS_V5 = fromString("Standard_E8-2ds_v5");

  public static final AzureMachineType STANDARD_E8_2S_V3 = fromString("Standard_E8-2s_v3");

  public static final AzureMachineType STANDARD_E8_2S_V4 = fromString("Standard_E8-2s_v4");

  public static final AzureMachineType STANDARD_E8_2S_V5 = fromString("Standard_E8-2s_v5");

  public static final AzureMachineType STANDARD_E8_4ADS_V5 = fromString("Standard_E8-4ads_v5");

  public static final AzureMachineType STANDARD_E8_4AS_V4 = fromString("Standard_E8-4as_v4");

  public static final AzureMachineType STANDARD_E8_4AS_V5 = fromString("Standard_E8-4as_v5");

  public static final AzureMachineType STANDARD_E8_4DS_V4 = fromString("Standard_E8-4ds_v4");

  public static final AzureMachineType STANDARD_E8_4DS_V5 = fromString("Standard_E8-4ds_v5");

  public static final AzureMachineType STANDARD_E8_4S_V3 = fromString("Standard_E8-4s_v3");

  public static final AzureMachineType STANDARD_E8_4S_V4 = fromString("Standard_E8-4s_v4");

  public static final AzureMachineType STANDARD_E8_4S_V5 = fromString("Standard_E8-4s_v5");

  public static final AzureMachineType STANDARD_E80IDS_V4 = fromString("Standard_E80ids_v4");
  public static final AzureMachineType STANDARD_E80IS_V4 = fromString("Standard_E80is_v4");
  public static final AzureMachineType STANDARD_E8ADS_V5 = fromString("Standard_E8ads_v5");
  public static final AzureMachineType STANDARD_E8AS_V4 = fromString("Standard_E8as_v4");
  public static final AzureMachineType STANDARD_E8AS_V5 = fromString("Standard_E8as_v5");
  public static final AzureMachineType STANDARD_E8A_V4 = fromString("Standard_E8a_v4");
  public static final AzureMachineType STANDARD_E8BDS_V5 = fromString("Standard_E8bds_v5");
  public static final AzureMachineType STANDARD_E8BS_V5 = fromString("Standard_E8bs_v5");
  public static final AzureMachineType STANDARD_E8DS_V4 = fromString("Standard_E8ds_v4");
  public static final AzureMachineType STANDARD_E8DS_V5 = fromString("Standard_E8ds_v5");
  public static final AzureMachineType STANDARD_E8D_V4 = fromString("Standard_E8d_v4");
  public static final AzureMachineType STANDARD_E8D_V5 = fromString("Standard_E8d_v5");
  public static final AzureMachineType STANDARD_E8PDS_V5 = fromString("Standard_E8pds_v5");
  public static final AzureMachineType STANDARD_E8PS_V5 = fromString("Standard_E8ps_v5");
  public static final AzureMachineType STANDARD_E8S_V3 = fromString("Standard_E8s_v3");
  public static final AzureMachineType STANDARD_E8S_V4 = fromString("Standard_E8s_v4");
  public static final AzureMachineType STANDARD_E8S_V5 = fromString("Standard_E8s_v5");
  public static final AzureMachineType STANDARD_E8_V3 = fromString("Standard_E8_v3");
  public static final AzureMachineType STANDARD_E8_V4 = fromString("Standard_E8_v4");
  public static final AzureMachineType STANDARD_E8_V5 = fromString("Standard_E8_v5");
  public static final AzureMachineType STANDARD_E96_24ADS_V5 = fromString("Standard_E96-24ads_v5");

  public static final AzureMachineType STANDARD_E96_24AS_V4 = fromString("Standard_E96-24as_v4");

  public static final AzureMachineType STANDARD_E96_24AS_V5 = fromString("Standard_E96-24as_v5");

  public static final AzureMachineType STANDARD_E96_24DS_V5 = fromString("Standard_E96-24ds_v5");

  public static final AzureMachineType STANDARD_E96_24S_V5 = fromString("Standard_E96-24s_v5");

  public static final AzureMachineType STANDARD_E96_48ADS_V5 = fromString("Standard_E96-48ads_v5");

  public static final AzureMachineType STANDARD_E96_48AS_V4 = fromString("Standard_E96-48as_v4");

  public static final AzureMachineType STANDARD_E96_48AS_V5 = fromString("Standard_E96-48as_v5");

  public static final AzureMachineType STANDARD_E96_48DS_V5 = fromString("Standard_E96-48ds_v5");

  public static final AzureMachineType STANDARD_E96_48S_V5 = fromString("Standard_E96-48s_v5");

  public static final AzureMachineType STANDARD_E96ADS_V5 = fromString("Standard_E96ads_v5");
  public static final AzureMachineType STANDARD_E96AS_V4 = fromString("Standard_E96as_v4");
  public static final AzureMachineType STANDARD_E96AS_V5 = fromString("Standard_E96as_v5");
  public static final AzureMachineType STANDARD_E96A_V4 = fromString("Standard_E96a_v4");
  public static final AzureMachineType STANDARD_E96DS_V5 = fromString("Standard_E96ds_v5");
  public static final AzureMachineType STANDARD_E96D_V5 = fromString("Standard_E96d_v5");
  public static final AzureMachineType STANDARD_E96IAS_V4 = fromString("Standard_E96ias_v4");
  public static final AzureMachineType STANDARD_E96S_V5 = fromString("Standard_E96s_v5");
  public static final AzureMachineType STANDARD_E96_V5 = fromString("Standard_E96_v5");
  public static final AzureMachineType STANDARD_EC16ADS_V5 = fromString("Standard_EC16ads_v5");
  public static final AzureMachineType STANDARD_EC16AS_V5 = fromString("Standard_EC16as_v5");
  public static final AzureMachineType STANDARD_EC20ADS_V5 = fromString("Standard_EC20ads_v5");
  public static final AzureMachineType STANDARD_EC20AS_V5 = fromString("Standard_EC20as_v5");
  public static final AzureMachineType STANDARD_EC2ADS_V5 = fromString("Standard_EC2ads_v5");
  public static final AzureMachineType STANDARD_EC2AS_V5 = fromString("Standard_EC2as_v5");
  public static final AzureMachineType STANDARD_EC32ADS_V5 = fromString("Standard_EC32ads_v5");
  public static final AzureMachineType STANDARD_EC32AS_V5 = fromString("Standard_EC32as_v5");
  public static final AzureMachineType STANDARD_EC48ADS_V5 = fromString("Standard_EC48ads_v5");
  public static final AzureMachineType STANDARD_EC48AS_V5 = fromString("Standard_EC48as_v5");
  public static final AzureMachineType STANDARD_EC4ADS_V5 = fromString("Standard_EC4ads_v5");
  public static final AzureMachineType STANDARD_EC4AS_V5 = fromString("Standard_EC4as_v5");
  public static final AzureMachineType STANDARD_EC64ADS_V5 = fromString("Standard_EC64ads_v5");
  public static final AzureMachineType STANDARD_EC64AS_V5 = fromString("Standard_EC64as_v5");
  public static final AzureMachineType STANDARD_EC8ADS_V5 = fromString("Standard_EC8ads_v5");
  public static final AzureMachineType STANDARD_EC8AS_V5 = fromString("Standard_EC8as_v5");
  public static final AzureMachineType STANDARD_EC96ADS_V5 = fromString("Standard_EC96ads_v5");
  public static final AzureMachineType STANDARD_EC96AS_V5 = fromString("Standard_EC96as_v5");
  public static final AzureMachineType STANDARD_EC96IADS_V5 = fromString("Standard_EC96iads_v5");
  public static final AzureMachineType STANDARD_EC96IAS_V5 = fromString("Standard_EC96ias_v5");
  public static final AzureMachineType STANDARD_F1 = fromString("Standard_F1");
  public static final AzureMachineType STANDARD_F16 = fromString("Standard_F16");
  public static final AzureMachineType STANDARD_F16S = fromString("Standard_F16s");
  public static final AzureMachineType STANDARD_F16S_V2 = fromString("Standard_F16s_v2");
  public static final AzureMachineType STANDARD_F1S = fromString("Standard_F1s");
  public static final AzureMachineType STANDARD_F2 = fromString("Standard_F2");
  public static final AzureMachineType STANDARD_F2S = fromString("Standard_F2s");
  public static final AzureMachineType STANDARD_F2S_V2 = fromString("Standard_F2s_v2");
  public static final AzureMachineType STANDARD_F32S_V2 = fromString("Standard_F32s_v2");
  public static final AzureMachineType STANDARD_F4 = fromString("Standard_F4");
  public static final AzureMachineType STANDARD_F48S_V2 = fromString("Standard_F48s_v2");
  public static final AzureMachineType STANDARD_F4S = fromString("Standard_F4s");
  public static final AzureMachineType STANDARD_F4S_V2 = fromString("Standard_F4s_v2");
  public static final AzureMachineType STANDARD_F64S_V2 = fromString("Standard_F64s_v2");
  public static final AzureMachineType STANDARD_F72S_V2 = fromString("Standard_F72s_v2");
  public static final AzureMachineType STANDARD_F8 = fromString("Standard_F8");
  public static final AzureMachineType STANDARD_F8S = fromString("Standard_F8s");
  public static final AzureMachineType STANDARD_F8S_V2 = fromString("Standard_F8s_v2");
  public static final AzureMachineType STANDARD_FX12MDS = fromString("Standard_FX12mds");
  public static final AzureMachineType STANDARD_FX24MDS = fromString("Standard_FX24mds");
  public static final AzureMachineType STANDARD_FX36MDS = fromString("Standard_FX36mds");
  public static final AzureMachineType STANDARD_FX48MDS = fromString("Standard_FX48mds");
  public static final AzureMachineType STANDARD_FX4MDS = fromString("Standard_FX4mds");
  public static final AzureMachineType STANDARD_G1 = fromString("Standard_G1");
  public static final AzureMachineType STANDARD_G2 = fromString("Standard_G2");
  public static final AzureMachineType STANDARD_G3 = fromString("Standard_G3");
  public static final AzureMachineType STANDARD_G4 = fromString("Standard_G4");
  public static final AzureMachineType STANDARD_G5 = fromString("Standard_G5");
  public static final AzureMachineType STANDARD_GS1 = fromString("Standard_GS1");
  public static final AzureMachineType STANDARD_GS2 = fromString("Standard_GS2");
  public static final AzureMachineType STANDARD_GS3 = fromString("Standard_GS3");
  public static final AzureMachineType STANDARD_GS4 = fromString("Standard_GS4");
  public static final AzureMachineType STANDARD_GS4_4 = fromString("Standard_GS4-4");

  public static final AzureMachineType STANDARD_GS4_8 = fromString("Standard_GS4-8");

  public static final AzureMachineType STANDARD_GS5 = fromString("Standard_GS5");
  public static final AzureMachineType STANDARD_GS5_16 = fromString("Standard_GS5-16");

  public static final AzureMachineType STANDARD_GS5_8 = fromString("Standard_GS5-8");

  public static final AzureMachineType STANDARD_HB120_16RS_V2 = fromString("Standard_HB120-16rs_v2");

  public static final AzureMachineType STANDARD_HB120_16RS_V3 = fromString("Standard_HB120-16rs_v3");

  public static final AzureMachineType STANDARD_HB120_32RS_V2 = fromString("Standard_HB120-32rs_v2");

  public static final AzureMachineType STANDARD_HB120_32RS_V3 = fromString("Standard_HB120-32rs_v3");

  public static final AzureMachineType STANDARD_HB120_64RS_V2 = fromString("Standard_HB120-64rs_v2");

  public static final AzureMachineType STANDARD_HB120_64RS_V3 = fromString("Standard_HB120-64rs_v3");

  public static final AzureMachineType STANDARD_HB120_96RS_V2 = fromString("Standard_HB120-96rs_v2");

  public static final AzureMachineType STANDARD_HB120_96RS_V3 = fromString("Standard_HB120-96rs_v3");

  public static final AzureMachineType STANDARD_HB120RS_V2 = fromString("Standard_HB120rs_v2");
  public static final AzureMachineType STANDARD_HB120RS_V3 = fromString("Standard_HB120rs_v3");
  public static final AzureMachineType STANDARD_HB60_15RS = fromString("Standard_HB60-15rs");

  public static final AzureMachineType STANDARD_HB60_30RS = fromString("Standard_HB60-30rs");

  public static final AzureMachineType STANDARD_HB60_45RS = fromString("Standard_HB60-45rs");

  public static final AzureMachineType STANDARD_HB60RS = fromString("Standard_HB60rs");
  public static final AzureMachineType STANDARD_HC44_16RS = fromString("Standard_HC44-16rs");

  public static final AzureMachineType STANDARD_HC44_32RS = fromString("Standard_HC44-32rs");

  public static final AzureMachineType STANDARD_HC44RS = fromString("Standard_HC44rs");
  public static final AzureMachineType STANDARD_L16AS_V3 = fromString("Standard_L16as_v3");
  public static final AzureMachineType STANDARD_L16S = fromString("Standard_L16s");
  public static final AzureMachineType STANDARD_L16S_V2 = fromString("Standard_L16s_v2");
  public static final AzureMachineType STANDARD_L16S_V3 = fromString("Standard_L16s_v3");
  public static final AzureMachineType STANDARD_L32AS_V3 = fromString("Standard_L32as_v3");
  public static final AzureMachineType STANDARD_L32S = fromString("Standard_L32s");
  public static final AzureMachineType STANDARD_L32S_V2 = fromString("Standard_L32s_v2");
  public static final AzureMachineType STANDARD_L32S_V3 = fromString("Standard_L32s_v3");
  public static final AzureMachineType STANDARD_L48AS_V3 = fromString("Standard_L48as_v3");
  public static final AzureMachineType STANDARD_L48S_V2 = fromString("Standard_L48s_v2");
  public static final AzureMachineType STANDARD_L48S_V3 = fromString("Standard_L48s_v3");
  public static final AzureMachineType STANDARD_L4S = fromString("Standard_L4s");
  public static final AzureMachineType STANDARD_L64AS_V3 = fromString("Standard_L64as_v3");
  public static final AzureMachineType STANDARD_L64S_V2 = fromString("Standard_L64s_v2");
  public static final AzureMachineType STANDARD_L64S_V3 = fromString("Standard_L64s_v3");
  public static final AzureMachineType STANDARD_L80AS_V3 = fromString("Standard_L80as_v3");
  public static final AzureMachineType STANDARD_L80S_V2 = fromString("Standard_L80s_v2");
  public static final AzureMachineType STANDARD_L80S_V3 = fromString("Standard_L80s_v3");
  public static final AzureMachineType STANDARD_L8AS_V3 = fromString("Standard_L8as_v3");
  public static final AzureMachineType STANDARD_L8S = fromString("Standard_L8s");
  public static final AzureMachineType STANDARD_L8S_V2 = fromString("Standard_L8s_v2");
  public static final AzureMachineType STANDARD_L8S_V3 = fromString("Standard_L8s_v3");
  public static final AzureMachineType STANDARD_M128 = fromString("Standard_M128");
  public static final AzureMachineType STANDARD_M128_32MS = fromString("Standard_M128-32ms");

  public static final AzureMachineType STANDARD_M128_64MS = fromString("Standard_M128-64ms");

  public static final AzureMachineType STANDARD_M128DMS_V2 = fromString("Standard_M128dms_v2");
  public static final AzureMachineType STANDARD_M128DS_V2 = fromString("Standard_M128ds_v2");
  public static final AzureMachineType STANDARD_M128M = fromString("Standard_M128m");
  public static final AzureMachineType STANDARD_M128MS = fromString("Standard_M128ms");
  public static final AzureMachineType STANDARD_M128MS_V2 = fromString("Standard_M128ms_v2");
  public static final AzureMachineType STANDARD_M128S = fromString("Standard_M128s");
  public static final AzureMachineType STANDARD_M128S_V2 = fromString("Standard_M128s_v2");
  public static final AzureMachineType STANDARD_M16_4MS = fromString("Standard_M16-4ms");

  public static final AzureMachineType STANDARD_M16_8MS = fromString("Standard_M16-8ms");

  public static final AzureMachineType STANDARD_M16MS = fromString("Standard_M16ms");
  public static final AzureMachineType STANDARD_M192IDMS_V2 = fromString("Standard_M192idms_v2");
  public static final AzureMachineType STANDARD_M192IDS_V2 = fromString("Standard_M192ids_v2");
  public static final AzureMachineType STANDARD_M192IMS_V2 = fromString("Standard_M192ims_v2");
  public static final AzureMachineType STANDARD_M192IS_V2 = fromString("Standard_M192is_v2");
  public static final AzureMachineType STANDARD_M208MS_V2 = fromString("Standard_M208ms_v2");
  public static final AzureMachineType STANDARD_M208S_V2 = fromString("Standard_M208s_v2");
  public static final AzureMachineType STANDARD_M32_16MS = fromString("Standard_M32-16ms");

  public static final AzureMachineType STANDARD_M32_8MS = fromString("Standard_M32-8ms");

  public static final AzureMachineType STANDARD_M32DMS_V2 = fromString("Standard_M32dms_v2");
  public static final AzureMachineType STANDARD_M32LS = fromString("Standard_M32ls");
  public static final AzureMachineType STANDARD_M32MS = fromString("Standard_M32ms");
  public static final AzureMachineType STANDARD_M32MS_V2 = fromString("Standard_M32ms_v2");
  public static final AzureMachineType STANDARD_M32TS = fromString("Standard_M32ts");
  public static final AzureMachineType STANDARD_M416_208MS_V2 = fromString("Standard_M416-208ms_v2");

  public static final AzureMachineType STANDARD_M416_208S_V2 = fromString("Standard_M416-208s_v2");

  public static final AzureMachineType STANDARD_M416MS_V2 = fromString("Standard_M416ms_v2");
  public static final AzureMachineType STANDARD_M416S_V2 = fromString("Standard_M416s_v2");
  public static final AzureMachineType STANDARD_M64 = fromString("Standard_M64");
  public static final AzureMachineType STANDARD_M64_16MS = fromString("Standard_M64-16ms");

  public static final AzureMachineType STANDARD_M64_32MS = fromString("Standard_M64-32ms");

  public static final AzureMachineType STANDARD_M64DMS_V2 = fromString("Standard_M64dms_v2");
  public static final AzureMachineType STANDARD_M64DS_V2 = fromString("Standard_M64ds_v2");
  public static final AzureMachineType STANDARD_M64LS = fromString("Standard_M64ls");
  public static final AzureMachineType STANDARD_M64M = fromString("Standard_M64m");
  public static final AzureMachineType STANDARD_M64MS = fromString("Standard_M64ms");
  public static final AzureMachineType STANDARD_M64MS_V2 = fromString("Standard_M64ms_v2");
  public static final AzureMachineType STANDARD_M64S = fromString("Standard_M64s");
  public static final AzureMachineType STANDARD_M64S_V2 = fromString("Standard_M64s_v2");
  public static final AzureMachineType STANDARD_M8_2MS = fromString("Standard_M8-2ms");

  public static final AzureMachineType STANDARD_M8_4MS = fromString("Standard_M8-4ms");

  public static final AzureMachineType STANDARD_M8MS = fromString("Standard_M8ms");
  public static final AzureMachineType STANDARD_NC12 = fromString("Standard_NC12");
  public static final AzureMachineType STANDARD_NC12S_V2 = fromString("Standard_NC12s_v2");
  public static final AzureMachineType STANDARD_NC12S_V3 = fromString("Standard_NC12s_v3");
  public static final AzureMachineType STANDARD_NC12_PROMO = fromString("Standard_NC12_Promo");
  public static final AzureMachineType STANDARD_NC16ADS_A10_V4 = fromString("Standard_NC16ads_A10_v4");
  public static final AzureMachineType STANDARD_NC16AS_T4_V3 = fromString("Standard_NC16as_T4_v3");
  public static final AzureMachineType STANDARD_NC24 = fromString("Standard_NC24");
  public static final AzureMachineType STANDARD_NC24ADS_A100_V4 = fromString("Standard_NC24ads_A100_v4");
  public static final AzureMachineType STANDARD_NC24R = fromString("Standard_NC24r");
  public static final AzureMachineType STANDARD_NC24RS_V2 = fromString("Standard_NC24rs_v2");
  public static final AzureMachineType STANDARD_NC24RS_V3 = fromString("Standard_NC24rs_v3");
  public static final AzureMachineType STANDARD_NC24R_PROMO = fromString("Standard_NC24r_Promo");
  public static final AzureMachineType STANDARD_NC24S_V2 = fromString("Standard_NC24s_v2");
  public static final AzureMachineType STANDARD_NC24S_V3 = fromString("Standard_NC24s_v3");
  public static final AzureMachineType STANDARD_NC24_PROMO = fromString("Standard_NC24_Promo");
  public static final AzureMachineType STANDARD_NC32ADS_A10_V4 = fromString("Standard_NC32ads_A10_v4");
  public static final AzureMachineType STANDARD_NC48ADS_A100_V4 = fromString("Standard_NC48ads_A100_v4");
  public static final AzureMachineType STANDARD_NC4AS_T4_V3 = fromString("Standard_NC4as_T4_v3");
  public static final AzureMachineType STANDARD_NC6 = fromString("Standard_NC6");
  public static final AzureMachineType STANDARD_NC64AS_T4_V3 = fromString("Standard_NC64as_T4_v3");
  public static final AzureMachineType STANDARD_NC6S_V2 = fromString("Standard_NC6s_v2");
  public static final AzureMachineType STANDARD_NC6S_V3 = fromString("Standard_NC6s_v3");
  public static final AzureMachineType STANDARD_NC6_PROMO = fromString("Standard_NC6_Promo");
  public static final AzureMachineType STANDARD_NC8ADS_A10_V4 = fromString("Standard_NC8ads_A10_v4");
  public static final AzureMachineType STANDARD_NC8AS_T4_V3 = fromString("Standard_NC8as_T4_v3");
  public static final AzureMachineType STANDARD_NC96ADS_A100_V4 = fromString("Standard_NC96ads_A100_v4");
  public static final AzureMachineType STANDARD_ND12S = fromString("Standard_ND12s");
  public static final AzureMachineType STANDARD_ND24RS = fromString("Standard_ND24rs");
  public static final AzureMachineType STANDARD_ND24S = fromString("Standard_ND24s");
  public static final AzureMachineType STANDARD_ND40RS_V2 = fromString("Standard_ND40rs_v2");
  public static final AzureMachineType STANDARD_ND40S_V3 = fromString("Standard_ND40s_v3");
  public static final AzureMachineType STANDARD_ND6S = fromString("Standard_ND6s");
  public static final AzureMachineType STANDARD_ND96AMSR_A100_V4 = fromString("Standard_ND96amsr_A100_v4");
  public static final AzureMachineType STANDARD_ND96ASR_V4 = fromString("Standard_ND96asr_v4");
  public static final AzureMachineType STANDARD_NP10S = fromString("Standard_NP10s");
  public static final AzureMachineType STANDARD_NP20S = fromString("Standard_NP20s");
  public static final AzureMachineType STANDARD_NP40S = fromString("Standard_NP40s");
  public static final AzureMachineType STANDARD_NV12 = fromString("Standard_NV12");
  public static final AzureMachineType STANDARD_NV12ADS_A10_V5 = fromString("Standard_NV12ads_A10_v5");
  public static final AzureMachineType STANDARD_NV12S_V2 = fromString("Standard_NV12s_v2");
  public static final AzureMachineType STANDARD_NV12S_V3 = fromString("Standard_NV12s_v3");
  public static final AzureMachineType STANDARD_NV12_PROMO = fromString("Standard_NV12_Promo");
  public static final AzureMachineType STANDARD_NV16AS_V4 = fromString("Standard_NV16as_v4");
  public static final AzureMachineType STANDARD_NV18ADS_A10_V5 = fromString("Standard_NV18ads_A10_v5");
  public static final AzureMachineType STANDARD_NV24 = fromString("Standard_NV24");
  public static final AzureMachineType STANDARD_NV24S_V2 = fromString("Standard_NV24s_v2");
  public static final AzureMachineType STANDARD_NV24S_V3 = fromString("Standard_NV24s_v3");
  public static final AzureMachineType STANDARD_NV24_PROMO = fromString("Standard_NV24_Promo");
  public static final AzureMachineType STANDARD_NV32AS_V4 = fromString("Standard_NV32as_v4");
  public static final AzureMachineType STANDARD_NV36ADMS_A10_V5 = fromString("Standard_NV36adms_A10_v5");
  public static final AzureMachineType STANDARD_NV36ADS_A10_V5 = fromString("Standard_NV36ads_A10_v5");
  public static final AzureMachineType STANDARD_NV48S_V3 = fromString("Standard_NV48s_v3");
  public static final AzureMachineType STANDARD_NV4AS_V4 = fromString("Standard_NV4as_v4");
  public static final AzureMachineType STANDARD_NV6 = fromString("Standard_NV6");
  public static final AzureMachineType STANDARD_NV6ADS_A10_V5 = fromString("Standard_NV6ads_A10_v5");
  public static final AzureMachineType STANDARD_NV6S_V2 = fromString("Standard_NV6s_v2");
  public static final AzureMachineType STANDARD_NV6_PROMO = fromString("Standard_NV6_Promo");
  public static final AzureMachineType STANDARD_NV72ADS_A10_V5 = fromString("Standard_NV72ads_A10_v5");
  public static final AzureMachineType STANDARD_NV8AS_V4 = fromString("Standard_NV8as_v4");
  public static final AzureMachineType STANDARD_PB6S= fromString("Standard_PB6s");

  public AzureMachineType() {
  }

  @JsonCreator
  public static AzureMachineType fromString(String name) {
    return fromString(name, AzureMachineType.class);
  }

  public static Collection<AzureMachineType> values() {
    return values(AzureMachineType.class);
  }
}
