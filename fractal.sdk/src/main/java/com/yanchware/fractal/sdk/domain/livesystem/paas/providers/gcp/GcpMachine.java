package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GcpMachine {
  E2_HIGH_CPU2("e2-highcpu-2"),
  E2_HIGH_CPU4("e2-highcpu-4"),
  E2_HIGH_CPU8("e2-highcpu-8"),
  E2_HIGH_CPU16("e2-highcpu-16"),
  E2_HIGH_CPU32("e2-highcpu-32"),
  E2_HIGH_MEM2("e2-highmem-2"),
  E2_HIGH_MEM4("e2-highmem-4"),
  E2_HIGH_MEM8("e2-highmem-8"),
  E2_HIGH_MEM16("e2-highmem-16"),
  E2_MEDIUM("e2-medium"),
  E2_MICRO("e2-micro"),
  E2_SMALL("e2-small"),
  E2_STANDARD2("e2-standard-2"),
  E2_STANDARD4("e2-standard-4"),
  E2_STANDARD8("e2-standard-8"),
  E2_STANDARD16("e2-standard-16"),
  E2_STANDARD32("e2-standard-32"),
  F1_MICRO("f1-micro"),
  G1_SMALL("g1-small"),
  N1_HIGH_CPU2("n1-highcpu-2"),
  N1_HIGH_CPU4("n1-highcpu-4"),
  N1_HIGH_CPU8("n1-highcpu-8"),
  N1_HIGH_CPU16("n1-highcpu-16"),
  N1_HIGH_CPU32("n1-highcpu-32"),
  N1_HIGH_CPU64("n1-highcpu-64"),
  N1_HIGH_CPU96("n1-highcpu-96"),
  N1_HIGH_MEM2("n1-highmem-2"),
  N1_HIGH_MEM4("n1-highmem-4"),
  N1_HIGH_MEM8("n1-highmem-8"),
  N1_HIGH_MEM16("n1-highmem-16"),
  N1_HIGH_MEM32("n1-highmem-32"),
  N1_HIGH_MEM64("n1-highmem-64"),
  N1_HIGH_MEM96("n1-highmem-96"),
  N1_STANDARD1("n1-standard-1"),
  N1_STANDARD2("n1-standard-2"),
  N1_STANDARD4("n1-standard-4"),
  N1_STANDARD8("n1-standard-8"),
  N1_STANDARD16("n1-standard-16"),
  N1_STANDARD32("n1-standard-32"),
  N1_STANDARD64("n1-standard-64"),
  N1_STANDARD96("n1-standard-96"),
  N2_HIGH_CPU2("n2-highcpu-2"),
  N2_HIGH_CPU4("n2-highcpu-4"),
  N2_HIGH_CPU8("n2-highcpu-8"),
  N2_HIGH_CPU16("n2-highcpu-16"),
  N2_HIGH_CPU32("n2-highcpu-32"),
  N2_HIGH_CPU48("n2-highcpu-48"),
  N2_HIGH_CPU64("n2-highcpu-64"),
  N2_HIGH_CPU80("n2-highcpu-80"),
  N2_HIGH_MEM2("n2-highmem-2"),
  N2_HIGH_MEM4("n2-highmem-4"),
  N2_HIGH_MEM8("n2-highmem-8"),
  N2_HIGH_MEM16("n2-highmem-16"),
  N2_HIGH_MEM32("n2-highmem-32"),
  N2_HIGH_MEM48("n2-highmem-48"),
  N2_HIGH_MEM64("n2-highmem-64"),
  N2_HIGH_MEM80("n2-highmem-80"),
  N2_STANDARD2("n2-standard-2"),
  N2_STANDARD4("n2-standard-4"),
  N2_STANDARD8("n2-standard-8"),
  N2_STANDARD16("n2-standard-16"),
  N2_STANDARD32("n2-standard-32"),
  N2_STANDARD48("n2-standard-48"),
  N2_STANDARD64("n2-standard-64"),
  N2_STANDARD80("n2-standard-80");

  private final String id;

  GcpMachine(final String id) {
    this.id = id;
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }

}
