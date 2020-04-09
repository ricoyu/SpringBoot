package com.sexyuncle.springboot.scp.service;

import com.loserico.json.jsonpath.JsonPathUtils;
import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.scp.entity.DeliverCentre;
import com.sexyuncle.springboot.scp.vo.DeliverCentreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class DeliverCentreService {

	@Autowired
	private EntityOperations entityOperations;

	@Autowired
	private CriteriaOperations criteriaOperations;

	public int uploadDeliverCentre(String deliverCentreJson) {
		List<DeliverCentreVO> deliverCentreVOs = JsonPathUtils.readListNode(deliverCentreJson, "$", DeliverCentreVO.class);
		List<Long> deliverCentreIds = deliverCentreVOs.stream()
				.map(DeliverCentreVO::getDeliverCenterId)
				.distinct()
				.collect(toList());
		
		List<DeliverCentre> deliverCentres = criteriaOperations.findIn(DeliverCentre.class, "centreId", deliverCentreIds);
		List<Long> existingDeliverCentreIds = deliverCentres.stream()
				.map(DeliverCentre::getCentreId)
				.collect(toList());
		List<DeliverCentre> newDeliverCentres = deliverCentreVOs.stream()
				.filter((deliverCentreVo) -> !existingDeliverCentreIds.contains(deliverCentreVo.getDeliverCenterId()))
				.sorted((prev, next) -> prev.getDeliverCenterId().compareTo(next.getDeliverCenterId()))
				.map((deliverCentreVo) -> {
					DeliverCentre deliverCentre = new DeliverCentre();
					deliverCentre.setCentreId(deliverCentreVo.getDeliverCenterId());
					deliverCentre.setCentreName(deliverCentreVo.getDeliverCenterName());
					return deliverCentre;
				}).collect(toList());

		return entityOperations.save(newDeliverCentres).size();
	}
}
