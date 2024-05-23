package com.tec02.model.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.tec02.util.Util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {
	protected Set<Long> ids;
	protected Set<String> sns;
	protected Set<String> contains;
	protected Set<String> items;
	protected String sn;
	protected String mlbsn;
	protected String mo;
	protected String ethernetmac;
	protected String mode;
	protected String pnname;
	protected String pcname;
	protected String test_software_version;
	protected String position;
	protected String error_code;
	protected String error_details;
	protected String status;
	protected String onSfis;
	private String product;
	private String station;
	private String line;
	protected String start_time;
	protected String finish_time;

	public Instant getStart_time() {
		if (start_time != null) {
			if (start_time.matches("^[0-9]+")) {
				return Util.longCvtInstant(Long.valueOf(start_time));
			} else {
				return Util.stringCvtInstant(start_time, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS",
						"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd_HH-mm-ss");
			}
		}
		return null;
	}

	public Instant getFinish_time() {
		if (finish_time != null) {
			if (finish_time.matches("^[0-9]+")) {
				return Util.longCvtInstant(Long.valueOf(finish_time));
			} else {
				return Util.stringCvtInstant(finish_time, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS",
						"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd_HH-mm-ss");
			}
		}
		return null;
	}

	public Set<Long> getIds() {
		if (ids == null) {
			return new HashSet<>();
		}
		return ids;
	}

	public Set<String> getSns() {
		if (sns == null) {
			return new HashSet<>();
		}
		return sns;
	}

	public Set<String> getContains() {
		if (contains == null) {
			return new HashSet<>();
		}
		return contains;
	}

	public Set<String> getItems() {
		if (items == null) {
			return new HashSet<>();
		}
		return items;
	}

	public String getSn() {
		if(sn == null) return "";
		return sn;
	}

	public String getMlbsn() {
		if(mlbsn == null) return "";
		return mlbsn;
	}

	public String getMo() {
		if(mo == null) return "";
		return mo;
	}

	public String getEthernetmac() {
		if(ethernetmac == null) return "";
		return ethernetmac;
	}

	public String getMode() {
		if(mode == null) return "";
		return mode;
	}

	public String getPnname() {
		if(pnname == null) return "";
		return pnname;
	}

	public String getPcname() {
		if(pcname == null) return "";
		return pcname;
	}

	public String getTest_software_version() {
		if(test_software_version == null) return "";
		return test_software_version;
	}

	public String getPosition() {
		if(position == null) return "";
		return position;
	}

	public String getError_code() {
		if(error_code == null) return "";
		return error_code;
	}

	public String getError_details() {
		if(error_details == null) return "";
		return error_details;
	}

	public String getStatus() {
		if(status == null) return "";
		return status;
	}

	public String getOnSfis() {
		if(onSfis == null) return "";
		return onSfis;
	}

	public String getProduct() {
		if(product == null) return "";
		return product;
	}

	public String getStation() {
		if(station == null) return "";
		return station;
	}

	public String getLine() {
		if(line == null) return "";
		return line;
	}

}
