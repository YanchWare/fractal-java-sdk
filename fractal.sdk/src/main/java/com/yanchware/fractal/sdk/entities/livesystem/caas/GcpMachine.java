package com.yanchware.fractal.sdk.entities.livesystem.caas;

public enum GcpMachine {
  E2HighCpu2("e2-highcpu-2"),
  E2HighCpu4("e2-highcpu-4"),
  E2HighCpu8("e2-highcpu-8"),
  E2HighCpu16("e2-highcpu-16"),
  E2HighCpu32("e2-highcpu-32"),
  E2HighMem2("e2-highmem-2"),
  E2HighMem4("e2-highmem-4"),
  E2HighMem8("e2-highmem-8"),
  E2HighMem16("e2-highmem-16"),
  E2Medium("e2-medium"),
  E2Micro("e2-micro"),
  E2Small("e2-small"),
  E2Standard2("e2-standard-2"),
  E2Standard4("e2-standard-4"),
  E2Standard8("e2-standard-8"),
  E2Standard16("e2-standard-16"),
  E2Standard32("e2-standard-32"),
  F1Micro("f1-micro"),
  G1Small("g1-small"),
  N1HighCpu2("n1-highcpu-2"),
  N1HighCpu4("n1-highcpu-4"),
  N1HighCpu8("n1-highcpu-8"),
  N1HighCpu16("n1-highcpu-16"),
  N1HighCpu32("n1-highcpu-32"),
  N1HighCpu64("n1-highcpu-64"),
  N1HighCpu96("n1-highcpu-96"),
  N1HighMem2("n1-highmem-2"),
  N1HighMem4("n1-highmem-4"),
  N1HighMem8("n1-highmem-8"),
  N1HighMem16("n1-highmem-16"),
  N1HighMem32("n1-highmem-32"),
  N1HighMem64("n1-highmem-64"),
  N1HighMem96("n1-highmem-96"),
  N1Standard1("n1-standard-1"),
  N1Standard2("n1-standard-2"),
  N1Standard4("n1-standard-4"),
  N1Standard8("n1-standard-8"),
  N1Standard16("n1-standard-16"),
  N1Standard32("n1-standard-32"),
  N1Standard64("n1-standard-64"),
  N1Standard96("n1-standard-96"),
  N2HighCpu2("n2-highcpu-2"),
  N2HighCpu4("n2-highcpu-4"),
  N2HighCpu8("n2-highcpu-8"),
  N2HighCpu16("n2-highcpu-16"),
  N2HighCpu32("n2-highcpu-32"),
  N2HighCpu48("n2-highcpu-48"),
  N2HighCpu64("n2-highcpu-64"),
  N2HighCpu80("n2-highcpu-80"),
  N2HighMem2("n2-highmem-2"),
  N2HighMem4("n2-highmem-4"),
  N2HighMem8("n2-highmem-8"),
  N2HighMem16("n2-highmem-16"),
  N2HighMem32("n2-highmem-32"),
  N2HighMem48("n2-highmem-48"),
  N2HighMem64("n2-highmem-64"),
  N2HighMem80("n2-highmem-80"),
  N2Standard2("n2-standard-2"),
  N2Standard4("n2-standard-4"),
  N2Standard8("n2-standard-8"),
  N2Standard16("n2-standard-16"),
  N2Standard32("n2-standard-32"),
  N2Standard48("n2-standard-48"),
  N2Standard64("n2-standard-64"),
  N2Standard80("n2-standard-80");

  private final String id;

  GcpMachine(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}
