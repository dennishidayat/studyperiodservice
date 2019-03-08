package com.enigma.task.studyperiod;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.task.studyperiod.dao.StudyPeriodDao;
import com.enigma.task.studyperiod.dto.CommonResponse;
import com.enigma.task.studyperiod.dto.StudyPeriodDto;
import com.enigma.task.studyperiod.exception.CustomException;
import com.enigma.task.studyperiod.model.StudyPeriod;

@RestController
@RequestMapping("/studyperiod")
@SuppressWarnings("rawtypes")
public class StudyPeriodController {
	
	@Autowired
	public ModelMapper modelMapper;
	
	@Autowired
	public StudyPeriodDao studyPeriodDao;
	
	@GetMapping(value="/{sequence}")
	public CommonResponse<StudyPeriodDto> getById(@PathVariable("sequence") String sequence) throws CustomException {
		try {
			
			StudyPeriod studyPeriod = studyPeriodDao.getById(Integer.parseInt(sequence));
			
			return new CommonResponse<StudyPeriodDto>(modelMapper.map(studyPeriod, StudyPeriodDto.class));
			
		} catch (CustomException e) {
			return new CommonResponse<StudyPeriodDto>("06", "input must be a number");
		} catch (Exception e) {
			return new CommonResponse<StudyPeriodDto>("06", e.getMessage());
		}
	}
	
	@PostMapping(value="")
	public CommonResponse<StudyPeriodDto> insert(@RequestBody StudyPeriodDto studyPeriodDto) throws CustomException {
		try {

			StudyPeriod studyPeriod = modelMapper.map(studyPeriodDto, StudyPeriod.class);
			studyPeriod.setSequence(0);
			studyPeriod = studyPeriodDao.save(studyPeriod);
			
			return new CommonResponse<StudyPeriodDto>(modelMapper.map(studyPeriod, StudyPeriodDto.class));
			
		} catch (CustomException e) {
			return new CommonResponse<StudyPeriodDto>("14", "study period not found");
		} catch (NumberFormatException e) {
			return new CommonResponse<StudyPeriodDto>();
		} catch (Exception e) {
			return new CommonResponse<StudyPeriodDto>();
		}
	}
	
	@PutMapping(value="")
	public CommonResponse<StudyPeriodDto> update(@RequestBody StudyPeriodDto studyPeriodDto) {
		try {
			StudyPeriod checkStudyPeriod = studyPeriodDao.getById(studyPeriodDto.getSequence());
			if (checkStudyPeriod ==  null) {
				return new CommonResponse<StudyPeriodDto>("14", "trainee not found");
			}
			if (studyPeriodDto.getActiveFlag() != null) {
				checkStudyPeriod.setActiveFlag(studyPeriodDto.getActiveFlag());
			}
			if (studyPeriodDto.getDescription() != null) {
				checkStudyPeriod.setDescription(studyPeriodDto.getDescription());
			}
			
			checkStudyPeriod = studyPeriodDao.save(checkStudyPeriod);
			
			return new CommonResponse<StudyPeriodDto>(modelMapper.map(checkStudyPeriod, StudyPeriodDto.class));
		} catch (CustomException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@DeleteMapping(value="/{sequence}")
	public CommonResponse<StudyPeriodDto> delete(@PathVariable("sequence") String seq) throws CustomException {
		try {
			StudyPeriod studyPeriod = studyPeriodDao.getById(Integer.parseInt(seq));
			
			if (studyPeriod == null) {
				return new CommonResponse("06", "trainee not found");
			}
			studyPeriodDao.delete(studyPeriod);
			return new CommonResponse();
		} catch (CustomException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping("")
	public CommonResponse getList(@RequestParam(name="list", defaultValue="") String seq) throws CustomException {
		try {
			List<StudyPeriod> studyPeriod = studyPeriodDao.getList();
			
			return new CommonResponse<List<StudyPeriodDto>>(studyPeriod.stream().map(stdPeriod -> modelMapper.map(stdPeriod, StudyPeriodDto.class)).collect(Collectors.toList()));
		} catch (CustomException e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping("/active")
	public CommonResponse getListByActiveFlag(@RequestParam(name="list", defaultValue="") String seq) throws CustomException {
		try {
			List<StudyPeriod> studyPeriod = studyPeriodDao.getList();
			
			return new CommonResponse<List<StudyPeriodDto>>(studyPeriod.stream().map(stdPeriod -> modelMapper.map(stdPeriod, StudyPeriodDto.class)).collect(Collectors.toList()));
		} catch (CustomException e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}

}
